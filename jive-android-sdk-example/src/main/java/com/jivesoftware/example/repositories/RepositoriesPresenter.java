package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.utils.ActivityLauncher;
import com.jivesoftware.example.utils.AuthorizationReset;

import static com.jivesoftware.example.repositories.RepositoriesModel.Type.ORGANIZATIONS_REFRESH_FAILURE;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_FAILURE;
import static com.jivesoftware.example.repositories.RepositoriesModel.Type.USER_REPOS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoriesPresenter {
    public static void create(final Activity activity, final AuthorizationReset reset, final RepositoriesModel model, final RepositoriesView view, final View headerView, final ActivityLauncher launcher) {
        model.listenable.setListener(new IValueListener<Repository[]>() {
            @Override
            public void onPost(Repository[] repositories) {
                view.setRepositories(repositories);
            }
        }, USER_REPOS_REFRESH_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                reset.performReset();
            }
        }, USER_REPOS_REFRESH_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                reset.performReset();
            }
        }, ORGANIZATIONS_REFRESH_FAILURE);

        view.addHeader(headerView);

        model.refreshUserRepositories();
        model.refreshOrganizations();

        activity.getActionBar().setTitle(activity.getString(R.string.repositories));
        activity.setContentView(view);
    }

    public static void onCreateOptionsMenu(Activity activity, Menu menu) {
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.main, menu);
    }

    public static boolean onMenuItemSelected(Activity activity, MenuItem item, AuthorizationReset reset) {
        if (item.getItemId() == R.id.action_logout ) {
            reset.performReset();
        }
        return false;
    }
}
