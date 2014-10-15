package com.jivesoftware.example.github.authentication;

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

public class GitHubAuthenticationActivity extends Activity{
    @Inject
    GitHubAuthenticationModel model;
    @Inject
    GitHubAuthenticationView view;
    @Inject
    ActivityLauncher launcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new GitHubAuthenticationModule()).inject(this);
        GitHubAuthenticationPresenter.create(this, model, view, launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.jivesoftware.example.R.menu.main, menu);
        return true;
    }

    @Module( injects = GitHubAuthenticationActivity.class, includes = BaseModule.class )
    public class GitHubAuthenticationModule {
        @Provides
        public Context provideActivityContext() {
            return GitHubAuthenticationActivity.this;
        }
    }

}

