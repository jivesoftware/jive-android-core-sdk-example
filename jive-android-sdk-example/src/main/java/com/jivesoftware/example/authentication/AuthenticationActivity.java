package com.jivesoftware.example.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.utils.ActivityLauncher;
import com.jivesoftware.example.utils.PersistedKeyValueStore;

public class AuthenticationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitHubBasicAuthRequestInterceptor interceptor = new GitHubBasicAuthRequestInterceptor();
        AuthenticationModel model = new AuthenticationModel(interceptor, GitHubServiceFactory.createAuthService(interceptor, new AuthenticationErrorHandler()), new PersistedKeyValueStore(this));
        AuthenticationView view = new AuthenticationView(this);
        ActivityLauncher launcher = new ActivityLauncher();
        AuthenticationPresenter.create(this, model, view, launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.jivesoftware.example.R.menu.main, menu);
        return true;
    }
}

