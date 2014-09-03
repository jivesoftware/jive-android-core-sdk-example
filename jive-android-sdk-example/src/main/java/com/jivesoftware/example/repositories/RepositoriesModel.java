package com.jivesoftware.example.repositories;

import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_FAILURE;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoriesModel {
    public TypeListenable listenable = new TypeListenable();
    private final IGitHubRepoService repoService;

    public enum Type {
        USER_REPOS_REFRESH_SUCCESS,
        USER_REPOS_REFRESH_FAILURE,

        ORGANIZATIONS_REFRESH_SUCCESS
    }

    public RepositoriesModel(IGitHubRepoService repoService) {
        this.repoService = repoService;
    }

    public void refreshUserRepositories() {
         repoService.getUserRepositories(new Callback<Repository[]>() {
             @Override
             public void success(Repository[] repositories, Response response) {
                listenable.post(repositories, USER_REPOS_REFRESH_SUCCESS);
             }

             @Override
             public void failure(RetrofitError error) {
                listenable.post(USER_REPOS_REFRESH_FAILURE);
             }
         });
    }

    public void refreshOrganizations() {
        repoService.getOrganizations(new Callback<Organization[]>() {
            @Override
            public void success(Organization[] organizations, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
