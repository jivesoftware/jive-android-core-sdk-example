package com.jivesoftware.example.collaborators;

import com.jivesoftware.example.github.service.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.URLUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_REFRESH_FAILURE;
import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/22/14.
 */
@Singleton
public class CollaboratorModel {
    public final TypeListenable listenable;
    private final IGitHubRepoService repoService;
    private final Repository repository;

    public enum Type {
        COLLABORATOR_REFRESH_SUCCESS,
        COLLABORATOR_REFRESH_FAILURE
    }

    @Inject
    public CollaboratorModel(IGitHubRepoService repoService, Repository repository, TypeListenable typeListenable) {
        this.listenable = typeListenable;
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
