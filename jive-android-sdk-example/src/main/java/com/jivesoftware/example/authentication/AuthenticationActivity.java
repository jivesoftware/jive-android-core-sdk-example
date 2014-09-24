package com.jivesoftware.example.authentication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.ActivityLauncher;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

public class AuthenticationActivity extends Activity {

    @Inject
    AuthenticationModel model;
    @Inject
    AuthenticationView view;
    @Inject
    ActivityLauncher launcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph.create(new AuthenticationModule()).inject(this);

        AuthenticationPresenter.create(this, model, view, launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.jivesoftware.example.R.menu.main, menu);
        return true;
    }

    @Module( injects = AuthenticationActivity.class, includes = BaseModule.class )
    public class AuthenticationModule {
        @Provides public Context provideActivityContext() {
            return AuthenticationActivity.this;
        }
    }
}

