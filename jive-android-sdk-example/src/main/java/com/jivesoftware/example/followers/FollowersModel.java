package com.jivesoftware.example.followers;

import com.jivesoftware.android.mobile.sdk.entity.PersonEntity;
import com.jivesoftware.android.mobile.sdk.entity.PersonListEntity;
import com.jivesoftware.example.followers.events.FollowersUpdate;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.github.service.IGitHubRepoService;
import com.jivesoftware.example.jive.dao.JiveConnection;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.URLUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_ERROR;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_SUCCESS;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWER_ADD_FAILURE;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWER_ADD_SUCCESS;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWER_INVITE_SUCCESS;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersModel {
    private IGitHubRepoService gitHubRepoService;
    public TypeListenable listenable;
    private Repository repository;
    private final Team team;
    private JiveConnection connection;

    public enum Type {
        FOLLOWERS_SUCCESS,
        FOLLOWERS_ERROR,
        FOLLOWER_ADD_SUCCESS,
        FOLLOWER_INVITE_SUCCESS,
        FOLLOWER_ADD_FAILURE
    }

    @Inject
    public FollowersModel(JiveConnection connection, IGitHubRepoService gitHubRepoService, TypeListenable listenable, Repository repository, Team team) {
        this.connection = connection;
        this.gitHubRepoService = gitHubRepoService;
        this.listenable = listenable;
        this.repository = repository;
        this.team = team;
    }

    public void refresh() {
        connection.fetchMePerson(new JiveResultCallback<PersonEntity>() {
            @Override
            public void success(PersonEntity me) {
                connection.fetchFollowing(URLUtils.getPath(me.resources.get("following").ref), new JiveResultCallback<PersonListEntity>() {
                    @Override
                    public void success(PersonListEntity followers) {
                        processFollowers(followers);
                    }

                    @Override
                    public void failure() {
                        listenable.post(FOLLOWERS_ERROR);
                    }
                });
            };

            @Override
            public void failure() {
                listenable.post(FOLLOWERS_ERROR);
            }
        });
    }

    public void addUserAsCollaborator(User user) {
        if ( repository != null ) {
            gitHubRepoService.putCollaborator(URLUtils.getPath(repository.url),user.login, new Callback<Void>() {
                @Override
                public void success(Void aVoid, Response response) {
                    listenable.post(FOLLOWER_ADD_SUCCESS);
                }

                @Override
                public void failure(RetrofitError error) {
                    listenable.post(FOLLOWER_ADD_FAILURE);
                }
            });
        }

        if ( team != null ) {
            gitHubRepoService.putTeamMember(team.id,user.login, new Callback<Void>() {
                @Override
                public void success(Void aVoid, Response response) {
                    listenable.post(FOLLOWER_INVITE_SUCCESS);
                }

                @Override
                public void failure(RetrofitError error) {
                    listenable.post(FOLLOWER_ADD_FAILURE);
                }
            });
        }
    }

    private void processFollowers(PersonListEntity followers) {
        List<String> names = new ArrayList<String>();
        List<PersonEntity> people = followers.list;
        for ( PersonEntity person : people ) {
            names.add(person.name.formatted);
        }
        listenable.post(new FollowersUpdate(names), FOLLOWERS_SUCCESS);
    }
}
