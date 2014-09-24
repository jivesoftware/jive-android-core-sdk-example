package com.jivesoftware.example.teams;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.IntentExtraNames;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsActivity extends Activity {
    @Inject
    TeamsModel model;
    @Inject
    TeamsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph.create(new TeamsModule()).inject(this);

        TeamsPresenter.create(this, model, view);
    }

    @Module( injects = TeamsActivity.class, includes = BaseModule.class )
    public class TeamsModule {
        @Provides
        public Context provideActivityContext() {
            return TeamsActivity.this;
        }

        @Provides public Repository provideRepository() {
            return TeamsActivity.this.getIntent().getParcelableExtra(IntentExtraNames.REPOSITORY);
        }
    }
}
