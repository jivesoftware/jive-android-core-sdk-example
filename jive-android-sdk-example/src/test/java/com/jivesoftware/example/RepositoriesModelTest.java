package com.jivesoftware.example;

import com.google.common.collect.Lists;
import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.repositories.RepositoriesModel;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.junit.Before;
import org.junit.Test;
import retrofit.Callback;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by stephen.mclaughry on 9/23/14.
 */
public class RepositoriesModelTest {

    private RepositoriesModel underTest;

    @Mocked
    private IGitHubRepoService repoService;
    @Mocked
    private TypeListenable typeListenable;
    @Mocked
    private Organization organization;
    @Mocked
    private Repository repository;

    @Before
    public void init() {
        underTest = new RepositoriesModel(repoService, typeListenable);
    }

    @Test
    public void shouldPostRepositoriesOnRefreshOrganizations() {
        // Set up this specific test
        final List<Callback<Organization[]>> orgCallbacks = Lists.newArrayList();
        final List<Callback<Repository[]>> repoCallbacks = Lists.newArrayList();

        new NonStrictExpectations() {{
            repoService.getOrganizations(withCapture(orgCallbacks));
            repoService.getRepositories(anyString, withCapture(repoCallbacks));
        }};

        // DO THE THING
        underTest.refreshOrganizations();

        // Fire the callbacks
        Organization[] orgs = { organization };
        orgCallbacks.get(0).success(orgs, null);

        Repository[] repos = { repository };
        repoCallbacks.get(0).success(repos, null);

        // Verify
        new Verifications() {{
            Repository[] capturedRepos;
            typeListenable.post(capturedRepos = withCapture(), RepositoriesModel.Type.USER_REPOS_REFRESH_SUCCESS);
            assertEquals(1, capturedRepos.length);
            assertSame(repository, capturedRepos[0]);
        }};
    }
}
