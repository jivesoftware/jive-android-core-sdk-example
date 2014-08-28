package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.github.GitHubRequestInterceptor;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.GitHubAuthServiceFactory;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by mark.schisler on 8/26/14.
 */
@RunWith(RobolectricTestRunner.class)
public class GitHubAuthServiceTest {

    private IGitHubAuthService testObject;
    private String username;
    private String password;
    private GitHubRequestInterceptor gitHubRequestInterceptor;
    private AuthenticationErrorHandler authErrorHandler;

    @Before
    public void setUp() {
        username = "jivelandstl";
        password = "G1thubRulz123!";
        gitHubRequestInterceptor = new GitHubRequestInterceptor(username, password, null);
        authErrorHandler = new AuthenticationErrorHandler();
        testObject = GitHubAuthServiceFactory.create(gitHubRequestInterceptor, authErrorHandler);
    }

    @Test
    public void testWhenAuthorizationRequestedThenItIsRetrieved() throws TwoFactorException, AuthenticationException {
        User user = testObject.getUser();
        assertEquals(user.login, username);
    }

    @Test(expected = TwoFactorException.class)
    public void testWhenTwoFactorAuthThenExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        gitHubRequestInterceptor.setUsername("jiveland-two-factor");
        gitHubRequestInterceptor.setPassword("G1thubRulz123!");
        testObject.getUser();
    }

    @Test(expected = AuthenticationException.class)
    public void testWhenBadPasswordThenAuthenticationExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        gitHubRequestInterceptor.setUsername("jivelandstl");
        gitHubRequestInterceptor.setPassword("badpass");
        testObject.getUser();
    }
}
