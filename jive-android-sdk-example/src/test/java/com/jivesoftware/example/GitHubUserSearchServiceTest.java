package com.jivesoftware.example;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubUserSearchService;
import com.jivesoftware.example.github.dao.List;
import com.jivesoftware.example.github.dao.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mark.schisler on 9/16/14.
 */
public class GitHubUserSearchServiceTest extends GitHubAbstractServiceTest {
    private IGitHubUserSearchService testObject;

    @Before
    public void setUp() throws AuthenticationException, TwoFactorException {
        super.setUp();
        testObject = GitHubServiceFactory.createUserSearchService(gitHubOauthRequestInterceptor, authErrorHandler);
    }

    @Test
    public void testWhenUserSearchedForThenTheyAreFound() throws AuthenticationException, TwoFactorException {
        List<User> list = testObject.getUsers("jive in:name");
        assertTrue(list.totalCount > 1);
        assertNotNull(list.items);
    }
}
