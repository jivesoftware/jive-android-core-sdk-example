package com.jivesoftware.example;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.exceptions.AuthenticationException;
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
public class GithubAuthServiceTest {

    private IGitHubAuthService testObject;
    private String username;
    private String password;

    @Before
    public void setUp() {
        username = "jivelandstl";
        password = "G1thubRulz123!";
        testObject = GitHubAuthServiceFactory.create(username, password, null, new AuthenticationErrorHandler());
    }

    @Test
    public void testWhenAuthorizationRequestedThenItIsRetrieved() throws TwoFactorException, AuthenticationException {
        User user = testObject.getUser();
        assertEquals(user.login, username);
    }

    @Test(expected = TwoFactorException.class)
    public void testWhenTwoFactorAuthThenExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        testObject = GitHubAuthServiceFactory.create("jiveland-two-factor", "G1thubRulz123!", null, new AuthenticationErrorHandler());
        testObject.getUser();
    }

    @Test(expected = AuthenticationException.class)
    public void testWhenBadPasswordThenAuthenticationExceptionIsThrown() throws TwoFactorException, AuthenticationException {
        testObject = GitHubAuthServiceFactory.create("jivelandstl", "badpass", null, new AuthenticationErrorHandler());
        testObject.getUser();
    }
}
