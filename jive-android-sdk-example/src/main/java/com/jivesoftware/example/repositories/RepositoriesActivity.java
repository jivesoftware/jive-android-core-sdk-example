package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.authentication.GitHubAuthenticationErrorHandler;
import com.jivesoftware.example.github.service.IGitHubAuthService;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.profiles.ProfilesModel;
import com.jivesoftware.example.profiles.ProfilesPresenter;
import com.jivesoftware.example.profiles.ProfilesView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new RepositoriesModule()).inject(this);
        ProfilesPresenter.create(headerModel, headerView);
        RepositoriesPresenter.create(this, model, view, headerView);
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

