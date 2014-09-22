package com.jivesoftware.example.teams;

import android.app.Activity;
import android.os.Bundle;
import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.utils.IntentExtraNames;
import com.jivesoftware.example.utils.PersistedKeyValueStore;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersistedKeyValueStore store = new PersistedKeyValueStore(this);
        GitHubOauthRequestInterceptor interceptor = new GitHubOauthRequestInterceptor(store.getGithubToken());
        Repository repository = getIntent().getParcelableExtra(IntentExtraNames.REPOSITORY);
        TeamsModel model = new TeamsModel(GitHubServiceFactory.createRepoService(interceptor, new AuthenticationErrorHandler()),repository);
        TeamsPresenter.create(this, model, new TeamsView(this));
    }
}
