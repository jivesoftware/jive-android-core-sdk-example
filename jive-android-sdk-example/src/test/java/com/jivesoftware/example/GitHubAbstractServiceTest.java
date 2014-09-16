package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import org.junit.Before;

/**
 * Created by mark.schisler on 9/16/14.
 */
public class GitHubAbstractServiceTest {
    protected GitHubOauthRequestInterceptor gitHubOauthRequestInterceptor;
    protected AuthenticationErrorHandler authErrorHandler;

    @Before
    public void setUp() throws AuthenticationException, TwoFactorException {
        String username = "jivelandstl";
        String password = "G1thubRulz123!";

        GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor = new GitHubBasicAuthRequestInterceptor();
        gitHubBasicAuthRequestInterceptor.setUsername(username);
        gitHubBasicAuthRequestInterceptor.setPassword(password);

        authErrorHandler = new AuthenticationErrorHandler();
        IGitHubAuthService authService = GitHubServiceFactory.createAuthService(gitHubBasicAuthRequestInterceptor, authErrorHandler);
        AuthorizationRequest request = new AuthorizationRequest();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        request.note = getClass().getName();

        String token = authService.postAuthorization(request).token;
        gitHubOauthRequestInterceptor = new GitHubOauthRequestInterceptor(token);

    }
}
