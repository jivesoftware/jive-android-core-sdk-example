package com.jivesoftware.example;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class GitHubRepoServiceTest extends GitHubAbstractServiceTest {

    private IGitHubRepoService testObject;

    @Before
    public void setUp() throws AuthenticationException, TwoFactorException {
        super.setUp();
        testObject = GitHubServiceFactory.createRepoService(gitHubOauthRequestInterceptor, authErrorHandler);
    }

    @Test
    public void testWhenReposAreRequestedThenTheyAreRetrieved() throws AuthenticationException, TwoFactorException {
        Repository[] userRepositories = testObject.getUserRepositories();
        assertEquals(userRepositories.length, 2);
    }

    @Test
    public void testWhenOrganizationsAreRequestedThenTHeyAreRetrieved() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        assertEquals(organizations.length, 1);
    }
}
