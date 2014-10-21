package com.jivesoftware.example.profiles;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.jivesoftware.example.R;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.profiles.events.GitHubProfileEvent;
import com.jivesoftware.example.profiles.events.JiveProfileEvent;
import com.jivesoftware.example.utils.ActivityLauncher;
import com.jivesoftware.example.utils.AuthorizationReset;

import static com.jivesoftware.example.profiles.ProfilesModel.Type;
import static com.jivesoftware.example.profiles.ProfilesModel.Type.GITHUB_PROFILE_REFRESH;
import static com.jivesoftware.example.profiles.ProfilesModel.Type.JIVE_PROFILE_REFRESH;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class ProfilesPresenter {
    public static void create(final Activity activity, final AuthorizationReset reset, final ProfilesModel model, final ProfilesView view, final ActivityLauncher launcher) {
        model.listenable.setListener(new IValueListener<JiveProfileEvent>() {
            @Override
            public void onPost(JiveProfileEvent event) {
                view.refreshJiveProfile(event.name, event.avatarUrl);
            }
        }, JIVE_PROFILE_REFRESH);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                reset.performReset();
            }
        }, Type.PROFILE_ERROR);

        model.listenable.setListener(new IValueListener<GitHubProfileEvent>() {
            @Override
            public void onPost(GitHubProfileEvent event) {
                view.refreshGitHubProfile(event.name, event.avatarUrl);
            }
        }, GITHUB_PROFILE_REFRESH);

        model.refresh();
    }


}
