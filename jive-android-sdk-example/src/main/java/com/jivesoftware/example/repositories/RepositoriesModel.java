package com.jivesoftware.example.repositories;

import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.URLUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jivesoftware.example.repositories.RepositoriesModel.Type.ORGANIZATIONS_REFRESH_FAILURE;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.ORGANIZATIONS_REFRESH_SUCCESS;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_FAILURE;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/3/14.
 */
@Singleton
public class RepositoriesModel {
    public final TypeListenable listenable;
    private final IGitHubRepoService repoService;
    private Organization[] organizations;
    private List<Repository> repositories = new ArrayList<Repository>();

    public enum Type {
        USER_REPOS_REFRESH_SUCCESS,
        USER_REPOS_REFRESH_FAILURE,

        ORGANIZATIONS_REFRESH_SUCCESS,
        ORGANIZATIONS_REFRESH_FAILURE
    }

    @Inject
    public RepositoriesModel(IGitHubRepoService repoService, TypeListenable typeListenable) {
        this.repoService = repoService;
        this.listenable = typeListenable;
    }

    public void refreshUserRepositories() {
         repoService.getUserRepositories(new Callback<Repository[]>() {
             @Override
             public void success(Repository[] repositories, Response response) {
                 postRepositories(repositories);
             }

             @Override
             public void failure(RetrofitError error) {
                postRepositoriesFailure();
             }
         });
    }

    public void refreshOrganizations() {
        repoService.getOrganizations(new Callback<Organization[]>() {
            @Override
            public void success(Organization[] organizations, Response response) {
                RepositoriesModel.this.organizations = organizations;
                listenable.post(organizations, ORGANIZATIONS_REFRESH_SUCCESS);
                refreshOrganizationRepositories();
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(ORGANIZATIONS_REFRESH_FAILURE);
            }
        });
    }

    private void refreshOrganizationRepositories() {
        for ( Organization organization : organizations) {
            repoService.getRepositories(URLUtils.getPath(organization.reposUrl), new Callback<Repository[]>() {
                @Override
                public void success(Repository[] repositories, Response response) {
                    postRepositories(repositories);
                }

                @Override
                public void failure(RetrofitError error) {
                    postRepositoriesFailure();
                }
            });
        }
    }

    private void postRepositories(Repository[] repositories) {
        this.repositories.addAll(Arrays.asList(repositories));
        Repository[] repositoriesCopy = new Repository[this.repositories.size()];
        this.repositories.toArray(repositoriesCopy);
        listenable.post(repositoriesCopy, USER_REPOS_REFRESH_SUCCESS);
    }

    private void postRepositoriesFailure() {
        listenable.post(USER_REPOS_REFRESH_FAILURE);
    }
}
