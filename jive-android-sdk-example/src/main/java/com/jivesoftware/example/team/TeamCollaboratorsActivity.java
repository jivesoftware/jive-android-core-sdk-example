package com.jivesoftware.example.team;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.IntentExtraNames;
import com.jivesoftware.example.utils.ToastMaker;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorsActivity extends Activity {
    @Inject
    Team team;
    @Inject
    ToastMaker toastMaker;
    @Inject
    TeamCollaboratorsModel model;
    @Inject
    TeamCollaboratorsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new TeamCollaboratorsModule()).inject(this);
        TeamCollaboratorsPresenter.create(this, model, view, team, toastMaker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TeamCollaboratorsPresenter.onCreateOptionsMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (TeamCollaboratorsPresenter.onMenuItemSelected(this, item, team)) return  true;
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TeamCollaboratorsPresenter.resume(model);
    }

    @Module( injects = TeamCollaboratorsActivity.class, includes = BaseModule.class )
    public class TeamCollaboratorsModule {
        @Provides
        public Context provideActivityContext() {
            return TeamCollaboratorsActivity.this;
        }
        @Provides public Team provideRepository() {
            return TeamCollaboratorsActivity.this.getIntent().getParcelableExtra(IntentExtraNames.TEAM);
        }
    }

}
