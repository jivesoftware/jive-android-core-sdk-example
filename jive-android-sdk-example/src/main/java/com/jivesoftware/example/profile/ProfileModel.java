package com.jivesoftware.example.profile;

import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.github.service.IGitHubUserService;
import com.jivesoftware.example.listenable.TypeListenable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

import static com.jivesoftware.example.profile.ProfileModel.Type.PROFILE_REFRESH_FAILURE;
import static com.jivesoftware.example.profile.ProfileModel.Type.PROFILE_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class ProfileModel {
    public final TypeListenable listenable;
    private final IGitHubUserService gitHubUserService;
    private final User user;

    public enum Type {
        PROFILE_REFRESH_SUCCESS,
        PROFILE_REFRESH_FAILURE
    }

    @Inject
    public ProfileModel(TypeListenable listenable, IGitHubUserService gitHubUserService, User user) {
        this.listenable = listenable;
        this.gitHubUserService = gitHubUserService;
        this.user = user;
    }

    public void refresh() {
        gitHubUserService.getUser(user.login, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                listenable.post(user, PROFILE_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(PROFILE_REFRESH_FAILURE);
            }
        });
    }

}
