package com.jivesoftware.example;

import android.app.ActionBar;
import android.app.Activity;
import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.repositories.RepositoriesModel;
import com.jivesoftware.example.repositories.RepositoriesPresenter;
import com.jivesoftware.example.repositories.RepositoriesView;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by stephen.mclaughry on 10/2/14.
 */
public class RepositoriesPresenterTest {

    @Mocked
    private Activity activity;
    @Mocked
    private IGitHubRepoService repoService;
    @Mocked
    private TypeListenable listenable;

    private RepositoriesModel model;
    @Mocked
    private RepositoriesView view;

    @Mocked
    private ActionBar actionBar;

    @Before
    public void init() {
        model = new RepositoriesModel(repoService, listenable);
    }

    @Test
    public void testThatThePresenterWiresUpTheModelAndView() {
        new NonStrictExpectations() {{
            activity.getActionBar(); result = actionBar;
            activity.getString(R.string.repositories); result = "mocked";
        }};

        RepositoriesPresenter.create(activity, model, view);

        new Verifications() {{
            actionBar.setTitle("mocked");
            activity.setContentView(view);
        }};
    }
}
