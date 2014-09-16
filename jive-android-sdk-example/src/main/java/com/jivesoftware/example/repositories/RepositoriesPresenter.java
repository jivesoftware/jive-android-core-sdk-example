package com.jivesoftware.example.repositories;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.IValueListener;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoriesPresenter {
    public static void create(final Activity activity, final RepositoriesModel model, final RepositoriesView view) {
        model.listenable.setListener(new IValueListener<Repository[]>() {
            @Override
            public void onPost(Repository[] repositories) {
                view.setRepositories(repositories);
            }
        }, RepositoriesModel.Type.USER_REPOS_REFRESH_SUCCESS);

        model.refreshUserRepositories();
        model.refreshOrganizations();
        activity.getActionBar().setTitle(activity.getString(R.string.repositories));
        activity.setContentView(view);
    }
}
