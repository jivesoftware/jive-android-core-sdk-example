package com.jivesoftware.example.jive.authentication;

import android.app.Activity;
import com.jivesoftware.example.jive.authentication.events.JiveLoginPressed;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.repositories.RepositoriesActivity;
import com.jivesoftware.example.utils.ActivityLauncher;

import static com.jivesoftware.example.jive.authentication.JiveAuthenticationModel.Type.AUTH_FAILURE;
import static com.jivesoftware.example.jive.authentication.JiveAuthenticationModel.Type.AUTH_SUCCESS;
import static com.jivesoftware.example.jive.authentication.JiveAuthenticationModel.Type.ENDPOINT_MALFORMED;
import static com.jivesoftware.example.jive.authentication.JiveAuthenticationView.Type.JIVE_LOGIN_PRESSED;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveAuthenticationPresenter {
    public static void create(final Activity activity, final JiveAuthenticationModel model, final JiveAuthenticationView view, final ActivityLauncher launcher) {
        view.listenable.setListener(new IValueListener<JiveLoginPressed>() {
            @Override
            public void onPost(JiveLoginPressed event) {
                model.setEndpoint(event.endpoint);
                model.setUsername(event.username);
                model.setPassword(event.password);
                model.obtainAuth();
            }
        }, JIVE_LOGIN_PRESSED);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showEndpointFailure();
            }
        }, ENDPOINT_MALFORMED);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                view.showAuthFailure();
            }
        }, AUTH_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                launcher.launch(activity, RepositoriesActivity.class);
            }
        }, AUTH_SUCCESS);

        activity.setContentView(view);
    }
}
