package com.jivesoftware.example;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.service.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.utils.URLUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        assertEquals(userRepositories.length, 3);
    }

    @Test
    public void testWhenOrganizationsAreRequestedThenTHeyAreRetrieved() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();

        assertEquals(organizations.length, 1);
    }

    @Test
    public void testWhenRepositoryIsRequestedThenItIsRetrieved() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        Organization organization = organizations[0];
        String repoUrl = URLUtils.getPath(organization.reposUrl);

        Repository[] repositories = testObject.getRepositories(repoUrl);

        Repository repository = repositories[0];
        assertNotNull(repository.fullName);
    }

    @Test
    public void testWhenOrgTeamsAreRequestedThenTheyCanBeRetrieved() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        Organization organization = organizations[0];

        Team[] organizationTeams = testObject.getOrganizationTeams(organization.login);

        assertEquals(organizationTeams.length, 2);
    }

    @Test
    public void testWhenTeamsAreListedThenTheyAreFound() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        Organization organization = organizations[0];
        String reposUrl = URLUtils.getPath(organization.reposUrl);
        Repository[] repositories = testObject.getRepositories(reposUrl);
        Repository repository = repositories[0];
        String repoUrl = URLUtils.getPath(repository.url);

        Team[] repoTeams = testObject.getRepoTeams(repoUrl);

        assertEquals(repoTeams.length, 1);
    }

    @Test
    public void testWhenTeamMemberIsAddedToTeamThenItIsSuccessful() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        Organization organization = organizations[0];
        Team[] organizationTeams = testObject.getOrganizationTeams(organization.login);
        Team team = organizationTeams[0];

        testObject.putTeamMember(team.id, "jivelandstl");
    }

    @Test
    public void testWhenUserCollaboratorsAreRequestedThenTeamsCanBeFound() throws AuthenticationException, TwoFactorException {
        Organization[] organizations = testObject.getOrganizations();
        Organization organization = organizations[0];
        String reposUrl = URLUtils.getPath(organization.reposUrl);
        Repository[] repositories = testObject.getRepositories(reposUrl);
        Repository repository = repositories[0];
        String repoUrl = URLUtils.getPath(repository.url);

        User[] collaborators = testObject.getCollaborators(repoUrl);

        assertEquals(collaborators.length, 1);
    }

    @Test
    public void testWhenCollaboratorIsPutThenItIsSuccessful() throws AuthenticationException, TwoFactorException {
        Repository[] repositories = testObject.getUserRepositories();
        Repository repository = repositories[1];
        String repoUrl = URLUtils.getPath(repository.url);

        testObject.putCollaborator(repoUrl, "jiveland-two-factor");
        User[] collaborators = testObject.getCollaborators(repoUrl);
        assertEquals(collaborators.length, 2);

        testObject.deleteCollaborator(repoUrl, "jiveland-two-factor");
        collaborators = testObject.getCollaborators(repoUrl);
        assertEquals(collaborators.length, 1);
    }
}
