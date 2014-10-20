package com.jivesoftware.example.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.injection.BaseModule;
import com.jivesoftware.example.utils.IntentExtraNames;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class ProfileActivity extends Activity {
    @Inject
    ProfileView view;
    @Inject
    User user;
    @Inject
    ProfileModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObjectGraph.create(new ProfileModule()).inject(this);
        ProfilePresenter.create(this, model, view, user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProfilePresenter.resume(model);
    }

    @Module( injects = ProfileActivity.class, includes = BaseModule.class )
    public class ProfileModule {
        @Provides public Context provideApplicationContext() {
            return ProfileActivity.this;
        }
        @Provides public User provideUser() {
            return ProfileActivity.this.getIntent().getParcelableExtra(IntentExtraNames.USER);
        }
    }
}
