package com.jivesoftware.example;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.service.IGitHubUserSearchService;
import com.jivesoftware.example.github.dao.GitHubList;
import com.jivesoftware.example.github.dao.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        GitHubList<User> list = testObject.getUsers("fullname:\"Mark Schisler\"");
        assertEquals(list.totalCount, 1);
        assertNotNull(list.items);
    }

    @Test
    public void testWhenTwoUsersSearchedForThenTheyAreFound() throws AuthenticationException, TwoFactorException {
        GitHubList<User> list = testObject.getUsers("fullname:\"Mark Schisler\" fullname:\"Jive Software\"");
        assertEquals(list.totalCount, 2);
        assertNotNull(list.items);
    }
}
