package com.jivesoftware.example.followers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.IntentExtraNames;
import com.jivesoftware.example.utils.ToastMaker;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersActivity extends Activity {
    @Inject GitHubUsersModel usersModel;
    @Inject FollowersModel model;
    @Inject FollowersView view;
    @Inject Repository repository;
    @Inject ToastMaker toastMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new FollowersModule()).inject(this);
        FollowersPresenter.create(this, usersModel, model, view, toastMaker);
    }

    @Module( injects = FollowersActivity.class, includes = BaseModule.class)
    public class FollowersModule {
        @Provides public Context provideActivityContext() {
            return FollowersActivity.this;
        }

        @Provides public Repository provideRepository() {
            return FollowersActivity.this.getIntent().getParcelableExtra(IntentExtraNames.REPOSITORY);
        }
    }
}
