package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import com.jivesoftware.example.github.dao.Repository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class GitHubRepoServiceTest {

    private IGitHubRepoService testObject;

    @Before
    public void setUp() throws AuthenticationException, TwoFactorException {
        String username = "jivelandstl";
        String password = "G1thubRulz123!";

        GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor = new GitHubBasicAuthRequestInterceptor();
        gitHubBasicAuthRequestInterceptor.setUsername(username);
        gitHubBasicAuthRequestInterceptor.setPassword(password);

        AuthenticationErrorHandler authErrorHandler = new AuthenticationErrorHandler();
        IGitHubAuthService authService = GitHubServiceFactory.createAuthService(gitHubBasicAuthRequestInterceptor, authErrorHandler);
        AuthorizationRequest request = new AuthorizationRequest();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        request.note = getClass().getName();

        String token = authService.postAuthorization(request).token;
        GitHubOauthRequestInterceptor gitHubOauthRequestInterceptor = new GitHubOauthRequestInterceptor(token);
        testObject = GitHubServiceFactory.createRepoService(gitHubOauthRequestInterceptor, authErrorHandler);
    }

    @Test
    public void testWhenReposAreRequestedThenTheyAreRetreived() throws AuthenticationException, TwoFactorException {
        Repository[] userRepositories = testObject.getUserRepositories();
        assertEquals(userRepositories.length, 2);
    }
}
