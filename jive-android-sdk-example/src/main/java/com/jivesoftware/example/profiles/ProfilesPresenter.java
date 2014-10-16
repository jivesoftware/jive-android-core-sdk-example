package com.jivesoftware.example.profiles;

import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.profiles.events.GitHubProfileEvent;
import com.jivesoftware.example.profiles.events.JiveProfileEvent;

import static com.jivesoftware.example.profiles.ProfilesModel.Type.GITHUB_PROFILE_REFRESH;
import static com.jivesoftware.example.profiles.ProfilesModel.Type.JIVE_PROFILE_REFRESH;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class ProfilesPresenter {
    public static void create(final ProfilesModel model, final ProfilesView view) {
        model.listenable.setListener(new IValueListener<JiveProfileEvent>() {
            @Override
            public void onPost(JiveProfileEvent event) {
                view.refreshJiveProfile(event.name, event.avatarUrl);
            }
        }, JIVE_PROFILE_REFRESH);

        model.listenable.setListener(new IValueListener<GitHubProfileEvent>() {
            @Override
            public void onPost(GitHubProfileEvent event) {
                view.refreshGitHubProfile(event.name, event.avatarUrl);
            }
        }, GITHUB_PROFILE_REFRESH);

        model.refresh();
    }
}
