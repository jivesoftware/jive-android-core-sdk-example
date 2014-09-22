package com.jivesoftware.example.users;

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
public class CollaboratorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersistedKeyValueStore store = new PersistedKeyValueStore(this);
        GitHubOauthRequestInterceptor interceptor = new GitHubOauthRequestInterceptor(store.getGithubToken());
        Repository repository = getIntent().getParcelableExtra(IntentExtraNames.REPOSITORY);
        CollaboratorModel model = new CollaboratorModel(GitHubServiceFactory.createRepoService(interceptor, new AuthenticationErrorHandler()),repository);
        CollaboratorPresenter.create(this, model, new UsersView(this));
    }
}
