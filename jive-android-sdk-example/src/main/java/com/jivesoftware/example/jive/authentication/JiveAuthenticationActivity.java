package com.jivesoftware.example.jive.authentication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.github.authentication.GitHubAuthenticationActivity;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.ActivityLauncher;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveAuthenticationActivity extends Activity {
    @Inject
    JiveAuthenticationModel model;

    @Inject
    JiveAuthenticationView view;

    @Inject
    ActivityLauncher activityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new JiveAuthenticationModule()).inject(this);
        JiveAuthenticationPresenter.create(this, model, view, activityLauncher);
    }

    @Module( injects = GitHubAuthenticationActivity.class, includes = BaseModule.class )
    public class JiveAuthenticationModule {
        @Provides
        public Context provideActivityContext() {
            return JiveAuthenticationActivity.this;
        }
    }
}
