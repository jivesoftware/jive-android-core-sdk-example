package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.dao.Authorization;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;

/**
 * Created by mark.schisler on 9/16/14.
 */
public class GitHubAbstractServiceTest {
    protected GitHubOauthRequestInterceptor gitHubOauthRequestInterceptor;
    protected AuthenticationErrorHandler authErrorHandler;
    private Authorization authorization;
    private IGitHubAuthService authService;

    @Before
    public void setUp() throws AuthenticationException, TwoFactorException {
        String username = "jivelandstl";
        String password = "G1thubRulz123!";

        GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor = new GitHubBasicAuthRequestInterceptor();
        gitHubBasicAuthRequestInterceptor.setUsername(username);
        gitHubBasicAuthRequestInterceptor.setPassword(password);

        authErrorHandler = new AuthenticationErrorHandler();
        authService = GitHubServiceFactory.createAuthService(gitHubBasicAuthRequestInterceptor, authErrorHandler);
        AuthorizationRequest request = new AuthorizationRequest();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        request.note = getClass().getName();
        request.scopes = new String [2];
        Arrays.asList("repo","admin:org").toArray(request.scopes);

        authorization = authService.postAuthorization(request);
        gitHubOauthRequestInterceptor = new GitHubOauthRequestInterceptor(authorization.token);

    }

    @After
    public void tearDown() throws AuthenticationException, TwoFactorException {
        authService.deleteAuthorization(authorization.id);
    }
}
