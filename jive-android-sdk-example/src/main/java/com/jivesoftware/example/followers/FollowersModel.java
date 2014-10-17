package com.jivesoftware.example.followers;

import com.jivesoftware.android.mobile.sdk.entity.PersonEntity;
import com.jivesoftware.android.mobile.sdk.entity.PersonListEntity;
import com.jivesoftware.example.followers.events.FollowersUpdate;
import com.jivesoftware.example.jive.dao.JiveConnection;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.BackgroundRunner;
import com.jivesoftware.example.utils.URLUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_ERROR;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_SUCCESS;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersModel {
    public TypeListenable listenable;
    private JiveConnection connection;

    public enum Type {
        FOLLOWERS_SUCCESS,
        FOLLOWERS_ERROR
    }

    @Inject
    public FollowersModel(JiveConnection connection, TypeListenable listenable) {
        this.connection = connection;
        this.listenable = listenable;
    }

    public void refresh() {
        connection.fetchMePerson(new BackgroundRunner.JiveResultCallback<PersonEntity>() {
            @Override
            public void success(PersonEntity me) {
                connection.fetchFollowing(URLUtils.getPath(me.resources.get("following").ref), new BackgroundRunner.JiveResultCallback<PersonListEntity>() {
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

    private void processFollowers(PersonListEntity followers) {
        List<String> names = new ArrayList<String>();
        List<PersonEntity> people = followers.list;
        for ( PersonEntity person : people ) {
            names.add(person.name.formatted);
        }
        listenable.post(new FollowersUpdate(names), FOLLOWERS_SUCCESS);
    }
}
