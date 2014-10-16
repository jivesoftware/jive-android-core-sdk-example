package com.jivesoftware.example.collaborators;

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
 * Created by mark.schisler on 9/22/14.
 */
public class CollaboratorActivity extends Activity {
    @Inject
    CollaboratorModel model;
    @Inject
    CollaboratorsView view;
    @Inject
    ToastMaker toastMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph.create(new CollaboratorModule()).inject(this);

        CollaboratorPresenter.create(this, toastMaker, model, view);
    }

    @Module( injects = CollaboratorActivity.class, includes = BaseModule.class)
    public class CollaboratorModule {
        @Provides public Context provideActivityContext() {
            return CollaboratorActivity.this;
        }

        @Provides public Repository provideRepository() {
            return CollaboratorActivity.this.getIntent().getParcelableExtra(IntentExtraNames.REPOSITORY);
        }
    }
}
