package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.authentication.GitHubAuthenticationErrorHandler;
import com.jivesoftware.example.github.service.IGitHubAuthService;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.profiles.ProfilesModel;
import com.jivesoftware.example.profiles.ProfilesPresenter;
import com.jivesoftware.example.profiles.ProfilesView;
import com.jivesoftware.example.utils.ActivityLauncher;
import com.jivesoftware.example.utils.AuthorizationReset;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesActivity extends Activity {
    @Inject
    RepositoriesModel model;
    @Inject
    RepositoriesView view;
    @Inject
    ProfilesView headerView;
    @Inject
    ProfilesModel headerModel;
    @Inject
    ActivityLauncher launcher;
    @Inject
    AuthorizationReset reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new RepositoriesModule()).inject(this);
        ProfilesPresenter.create(this, reset, headerModel, headerView, launcher);
        RepositoriesPresenter.create(this, reset, model, view, headerView, launcher);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        RepositoriesPresenter.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (RepositoriesPresenter.onMenuItemSelected(this, item, reset)) return  true;
        return super.onMenuItemSelected(featureId, item);
    }

    @Module( injects = RepositoriesActivity.class, includes = BaseModule.class )
    public class RepositoriesModule {
        @Provides
        public Context provideApplicationContext() {
            return RepositoriesActivity.this;
        }

        @Provides
        public IGitHubAuthService provideGitHubAuthService(GitHubOauthRequestInterceptor interceptor) {
            return GitHubServiceFactory.createAuthService(interceptor, new GitHubAuthenticationErrorHandler());
        }
    }
}

