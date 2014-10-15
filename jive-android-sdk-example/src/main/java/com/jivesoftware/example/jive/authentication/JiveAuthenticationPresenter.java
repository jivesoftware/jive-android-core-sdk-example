package com.jivesoftware.example.jive.authentication;

import android.app.Activity;
import com.jivesoftware.example.jive.authentication.events.JiveLoginPressed;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.utils.ActivityLauncher;

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

        activity.setContentView(view);
    }
}
