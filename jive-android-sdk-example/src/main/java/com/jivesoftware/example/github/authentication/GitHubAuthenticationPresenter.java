package com.jivesoftware.example.github.authentication;

import android.app.Activity;
import com.jivesoftware.example.jive.authentication.JiveAuthenticationActivity;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.repositories.RepositoriesActivity;
import com.jivesoftware.example.utils.ActivityLauncher;

import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.OAUTH_AUTHENTICATION_SUCCESS;

public class GitHubAuthenticationPresenter {
    public static void create(final Activity activity, final GitHubAuthenticationModel model, final GitHubAuthenticationView view, final ActivityLauncher launcher) {
        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                launcher.launch(activity, JiveAuthenticationActivity.class);
            }
        }, OAUTH_AUTHENTICATION_SUCCESS);

        activity.setContentView(view);

        if ( model.doTokensExist() ) {
            launcher.launchClearTop(activity, RepositoriesActivity.class);
        }
    }

}
