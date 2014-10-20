package com.jivesoftware.example.followers;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.followers.events.FollowerSelected;
import com.jivesoftware.example.followers.events.FollowersUpdate;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.utils.ToastMaker;

import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_ERROR;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWERS_SUCCESS;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWER_ADD_FAILURE;
import static com.jivesoftware.example.followers.FollowersModel.Type.FOLLOWER_ADD_SUCCESS;
import static com.jivesoftware.example.followers.FollowersView.Type.FOLLOWER_SELECTED;
import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_FAILURE;
import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersPresenter {
    public static void create(final Activity activity, final GitHubUsersModel usersModel, final FollowersModel model, final FollowersView view, final ToastMaker toastMaker) {
        model.listenable.setListener(new IValueListener<FollowersUpdate>() {
            @Override
            public void onPost(FollowersUpdate followersUpdate) {
                usersModel.refresh(followersUpdate.names);
            }
        }, FOLLOWERS_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.follower_search_error);
            }
        }, FOLLOWERS_ERROR);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.follower_add_success);
                activity.finish();
            }
        }, FOLLOWER_ADD_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.follower_add_error);
            }
        }, FOLLOWER_ADD_FAILURE);

        usersModel.listenable.setListener(new IValueListener<User[]>() {
            @Override
            public void onPost(User[] users) {
                view.setFollowers(users);
            }
        }, USERS_REFRESH_SUCCESS);

        usersModel.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.follower_search_error);
            }
        }, USERS_REFRESH_FAILURE);

        view.listenable.setListener(new IValueListener<FollowerSelected>() {
            @Override
            public void onPost(FollowerSelected event) {
                model.addUserAsCollaborator(event.user);
            }
        }, FOLLOWER_SELECTED);

        activity.getActionBar().setTitle(R.string.add_collaborator);
        activity.setContentView(view);
        model.refresh();
    }
}
