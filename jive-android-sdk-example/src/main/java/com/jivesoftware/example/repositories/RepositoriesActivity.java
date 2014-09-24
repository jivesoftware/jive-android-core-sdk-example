package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.injection.BaseModule;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph.create(new RepositoriesModule()).inject(this);

        RepositoriesPresenter.create(this, model, view);
    }

    @Module( injects = RepositoriesActivity.class, includes = BaseModule.class )
    public class RepositoriesModule {
        @Provides
        public Context provideApplicationContext() {
            return RepositoriesActivity.this;
        }
    }
}

