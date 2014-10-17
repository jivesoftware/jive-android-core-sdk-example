package com.jivesoftware.example.followers;

import android.app.Activity;
import com.jivesoftware.example.followers.events.FollowersUpdate;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;

import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_ERROR;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_SUCCESS;
import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_FAILURE;
import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersPresenter {
    public static void create(final Activity activity, final GitHubUsersModel usersModel, final FollowersModel model, final FollowersView view) {
        model.listenable.setListener(new IValueListener<FollowersUpdate>() {
            @Override
            public void onPost(FollowersUpdate followersUpdate) {
                usersModel.refresh(followersUpdate.names);
            }
        }, FOLLOWERS_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showSearchError();
            }
        }, FOLLOWERS_ERROR);

        usersModel.listenable.setListener(new IValueListener<User[]>() {
            @Override
            public void onPost(User[] users) {
                view.setFollowers(users);
            }
        }, USERS_REFRESH_SUCCESS);

        usersModel.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showSearchError();
            }
        }, USERS_REFRESH_FAILURE);

        activity.setContentView(view);
        model.refresh();
    }
}
