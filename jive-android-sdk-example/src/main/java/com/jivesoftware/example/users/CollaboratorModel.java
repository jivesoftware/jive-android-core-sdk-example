package com.jivesoftware.example.users;

import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.URLUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.jivesoftware.example.users.CollaboratorModel.Type.COLLABORATOR_REFRESH_FAILURE;
import static com.jivesoftware.example.users.CollaboratorModel.Type.COLLABORATOR_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class CollaboratorModel {
    public TypeListenable listenable;
    private IGitHubRepoService repoService;
    private Repository repository;

    public enum Type {
        COLLABORATOR_REFRESH_SUCCESS,
        COLLABORATOR_REFRESH_FAILURE
    }

    public CollaboratorModel(IGitHubRepoService repoService, Repository repository) {
        listenable = new TypeListenable();
        this.repoService = repoService;
        this.repository = repository;
    }

    public void refresh() {
        repoService.getCollaborators(URLUtils.getPath(repository.url), new Callback<User[]>() {
            @Override
            public void success(User[] users, Response response) {
                listenable.post(users, COLLABORATOR_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(COLLABORATOR_REFRESH_FAILURE);
            }
        });
    }
}
