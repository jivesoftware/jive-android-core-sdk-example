package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.dao.Authorization;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import com.jivesoftware.example.github.dao.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Test.None;

/**
 * Created by mark.schisler on 8/26/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GitHubAuthServiceTest {
    private IGitHubAuthService testObject;
    private String username;
    private String password;
    private GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor;
    private AuthenticationErrorHandler authErrorHandler;

    @Before
    public void setUp() {
        username = "jivelandstl";
        password = "G1thubRulz123!";

        gitHubBasicAuthRequestInterceptor = new GitHubBasicAuthRequestInterceptor();
        gitHubBasicAuthRequestInterceptor.setUsername(username);
        gitHubBasicAuthRequestInterceptor.setPassword(password);

        authErrorHandler = new AuthenticationErrorHandler();

        testObject = GitHubServiceFactory.createAuthService(gitHubBasicAuthRequestInterceptor, authErrorHandler);
    }

    @Test(expected = None.class)
    public void testWhenAuthorizationRequestedThenItIsRetrieved() throws TwoFactorException, AuthenticationException {
        User user = testObject.getSelf();
        assertEquals(user.login, username);
    }

    @Test(expected = TwoFactorException.class)
    public void testWhenTwoFactorAuthThenExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        gitHubBasicAuthRequestInterceptor.setUsername("jiveland-two-factor");
        gitHubBasicAuthRequestInterceptor.setPassword("G1thubRulz123!");
        testObject.getSelf();
    }

    @Test(expected = AuthenticationException.class)
    public void testWhenBadPasswordThenAuthenticationExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        gitHubBasicAuthRequestInterceptor.setUsername("jivelandstl");
        gitHubBasicAuthRequestInterceptor.setPassword("badpass");
        testObject.getSelf();
    }

    @Test(expected = None.class)
    public void testWhenAuthorizationIsCreatedThenItCanBeDeleted() throws AuthenticationException, TwoFactorException {
        testObject.getSelf();
        AuthorizationRequest request = new AuthorizationRequest();
        request.note = getClass().getName();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        Authorization authorization = testObject.postAuthorization(request);
        testObject.deleteAuthorization(authorization.id);
    }
}
