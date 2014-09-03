package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.os.Bundle;
import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.utils.PersistedKeyValueStore;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersistedKeyValueStore store = new PersistedKeyValueStore(this);
        GitHubOauthRequestInterceptor interceptor = new GitHubOauthRequestInterceptor(store.getGithubToken());
        RepositoriesModel model = new RepositoriesModel(GitHubServiceFactory.createRepoService(interceptor, new AuthenticationErrorHandler()));
        RepositoriesPresenter.create(this, model, new RepositoriesView(this));
    }
}

