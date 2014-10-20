package com.jivesoftware.example.profile;

import android.app.Activity;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IValueListener;

import static com.jivesoftware.example.profile.ProfileModel.Type.PROFILE_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class ProfilePresenter {

    public static void create(final Activity activity, final ProfileModel model, final ProfileView view, final User user) {
        model.listenable.setListener(new IValueListener<User>() {
            @Override
            public void onPost(User user) {
                view.refresh(user.avatarUrl, user.name, user.login, user.location);
            }
        }, PROFILE_REFRESH_SUCCESS);

        view.refresh(user.avatarUrl, user.name, user.login, user.location);
        activity.setContentView(view);
    }

    public static void resume(final ProfileModel model) {
        model.refresh();
    }
}
