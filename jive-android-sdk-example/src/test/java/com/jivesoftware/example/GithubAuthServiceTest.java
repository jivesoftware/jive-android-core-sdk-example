package com.jivesoftware.example;

import com.jivesoftware.example.github.GitHubAuthService;
import com.jivesoftware.example.github.GitHubAuthServiceFactory;
import com.jivesoftware.example.github.TwoFactorErrorHandler;
import com.jivesoftware.example.github.TwoFactorException;
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

    private GitHubAuthService testObject;
    private String username;
    private String password;

    @Before
    public void setUp() {
        username = "jivelandstl";
        password = "G1thubRulz123!";
        testObject = GitHubAuthServiceFactory.create(username, password, null, new TwoFactorErrorHandler());
    }

    @Test
    public void testWhenAuthorizationRequestedThenItIsRetrieved() throws TwoFactorException {
        User user = testObject.getUser();
        assertEquals(user.login, username);
    }

    @Test(expected = TwoFactorException.class)
    public void testWhenTwoFactorAuthThenExceptionIsThrown() throws TwoFactorException {
        testObject = GitHubAuthServiceFactory.create("jiveland-two-factor", "G1thubRulz123!", null, new TwoFactorErrorHandler());
        testObject.getUser();
    }

}
