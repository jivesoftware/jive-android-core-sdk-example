package com.jivesoftware.example.collaborators;

import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.github.service.IGitHubRepoService;
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

import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_DELETE_SUCCESS;
import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_REFRESH_FAILURE;
import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/22/14.
 */
@Singleton
public class CollaboratorModel {
    public final TypeListenable listenable;
    private final IGitHubRepoService repoService;
    private List<User> users;
    private String repositoryPath;

    public enum Type {
        COLLABORATOR_REFRESH_SUCCESS,
        COLLABORATOR_REFRESH_FAILURE,
        COLLABORATOR_DELETE_SUCCESS,
        COLLABORATOR_DELETE_FAILURE
    }

    @Inject
    public CollaboratorModel(IGitHubRepoService repoService, Repository repository, TypeListenable typeListenable) {
        this.listenable = typeListenable;
        this.repoService = repoService;
        this.repositoryPath = URLUtils.getPath(repository.url);
    }

    public void refresh() {
        repoService.getCollaborators(repositoryPath, new Callback<User[]>() {
            @Override
            public void success(User[] users, Response response) {
                CollaboratorModel.this.users = new ArrayList<User>(Arrays.asList(users));
                listenable.post(users, COLLABORATOR_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(COLLABORATOR_REFRESH_FAILURE);
            }
        });
    }

    public void deleteCollaborator(long id) {
        for ( User user : users){
            if ( Long.valueOf(user.id).equals(id)) {
                repoService.deleteCollaborator(repositoryPath,user.login, new Callback<Void>() {
                    @Override
                    public void success(Void aVoid, Response response) {
                        listenable.post(getUsersAsArray(), COLLABORATOR_REFRESH_SUCCESS);
                        listenable.post(COLLABORATOR_DELETE_SUCCESS);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        listenable.post(COLLABORATOR_DELETE_SUCCESS);
                    }
                });
                users.remove(user);
                break;
            }
        }
    }

    private User[] getUsersAsArray() {
        User[] usersCopy = new User[users.size()];
        users.toArray(usersCopy);
        return usersCopy;
    }
}
