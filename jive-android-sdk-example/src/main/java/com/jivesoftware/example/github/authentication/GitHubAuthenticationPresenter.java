package com.jivesoftware.example.github.authentication;

import android.app.Activity;
import com.jivesoftware.example.github.authentication.events.LoginPressed;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.repositories.RepositoriesActivity;
import com.jivesoftware.example.utils.ActivityLauncher;

import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.OAUTH_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.OAUTH_AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationView.Type.LOGIN_PRESSED;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class GitHubAuthenticationPresenter {
    public static void create(final Activity activity, final GitHubAuthenticationModel model, final GitHubAuthenticationView view, final ActivityLauncher launcher) {
        model.listenable.setListener(new IValueListener<User>() {
            @Override
            public void onPost(User user) {
                model.obtainOauthToken();
            }
        }, BASIC_AUTHENTICATION_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.hideMessage();
                launcher.launch(activity, RepositoriesActivity.class);
            }
        }, OAUTH_AUTHENTICATION_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
               view.showOauthFailure();
            }
        }, OAUTH_AUTHENTICATION_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showAuthenticationRequired();
            }
        }, BASIC_AUTHENTICATION_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showTwoFactorRequired();
            }
        }, BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED);

        view.listenable.setListener(new IValueListener<LoginPressed>() {
            @Override
            public void onPost(LoginPressed event) {
                model.setUsername(event.username);
                model.setPassword(event.password);
                model.setOnetime(event.onetime);
                model.obtainBasicAuth();
            }
        }, LOGIN_PRESSED);

        activity.setContentView(view);
    }

}
