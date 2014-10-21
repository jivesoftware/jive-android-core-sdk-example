package com.jivesoftware.example.profiles;

import com.jivesoftware.android.mobile.sdk.entity.PersonEntity;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.github.service.IGitHubAuthService;
import com.jivesoftware.example.jive.dao.JiveConnection;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.profiles.events.GitHubProfileEvent;
import com.jivesoftware.example.profiles.events.JiveProfileEvent;
import com.jivesoftware.example.utils.PersistedKeyValueStore;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

import static com.jivesoftware.example.profiles.ProfilesModel.Type.GITHUB_PROFILE_REFRESH;
import static com.jivesoftware.example.profiles.ProfilesModel.Type.JIVE_PROFILE_REFRESH;
import static com.jivesoftware.example.profiles.ProfilesModel.Type.PROFILE_ERROR;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class ProfilesModel {
    private JiveConnection jiveConnection;
    private IGitHubAuthService gitHubAuthService;
    private final PersistedKeyValueStore keyValueStore;
    public TypeListenable listenable;

    public enum Type {
        GITHUB_PROFILE_REFRESH,
        JIVE_PROFILE_REFRESH,
        PROFILE_ERROR
    }

    @Inject
    public ProfilesModel(JiveConnection jiveConnection, IGitHubAuthService gitHubAuthService, PersistedKeyValueStore keyValueStore, TypeListenable listenable) {
        this.jiveConnection = jiveConnection;
        this.gitHubAuthService = gitHubAuthService;
        this.keyValueStore = keyValueStore;
        this.listenable = listenable;
    }

    public void refresh() {
        jiveConnection.fetchMePerson(new JiveResultCallback<PersonEntity>() {
            @Override
            public void success(PersonEntity person) {
                String name = person.name.formatted;
                String avatarUrl = person.resources.get("avatar").ref;
                listenable.post(new JiveProfileEvent(name, avatarUrl), JIVE_PROFILE_REFRESH);
            }

            @Override
            public void failure() {
                listenable.post(PROFILE_ERROR);
            }
        });

        gitHubAuthService.getSelf(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                String name = user.login;
                String avatarUrl = user.avatarUrl;
                listenable.post(new GitHubProfileEvent(name, avatarUrl), GITHUB_PROFILE_REFRESH);

            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(PROFILE_ERROR);
            }
        });
    }
}
