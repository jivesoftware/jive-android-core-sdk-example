package com.jivesoftware.example.authentication;

import android.app.Activity;
import com.jivesoftware.example.authentication.events.LoginPressed;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.repositories.RepositoriesActivity;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.utils.ActivityLauncher;

import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_TWO_FACTOR_REQUIRED;
import static com.jivesoftware.example.authentication.AuthenticationView.Type.LOGIN_PRESSED;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class AuthenticationPresenter {
    public static void create(final Activity activity, final AuthenticationModel model, final AuthenticationView view, final ActivityLauncher launcher) {
        model.listenable.setListener(new IValueListener<User>() {
            @Override
            public void onPost(User user) {
                view.hideMessage();
                launcher.launch(activity, RepositoriesActivity.class);
            }
        }, AUTHENTICATION_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showAuthenticationRequired();
            }
        }, AUTHENTICATION_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showTwoFactorRequired();
            }
        }, AUTHENTICATION_TWO_FACTOR_REQUIRED);

        view.listenable.setListener(new IValueListener<LoginPressed>() {
            @Override
            public void onPost(LoginPressed event) {
                model.setUsername(event.username);
                model.setPassword(event.password);
                model.setOnetime(event.onetime);
                model.refresh();
            }
        }, LOGIN_PRESSED);

        activity.setContentView(view);
    }

}