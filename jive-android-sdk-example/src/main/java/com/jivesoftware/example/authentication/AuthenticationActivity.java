package com.jivesoftware.example.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.jivesoftware.example.github.GitHubAuthServiceFactory;
import com.jivesoftware.example.github.GitHubRequestInterceptor;
import com.jivesoftware.example.utils.ActivityLauncher;

public class AuthenticationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GitHubRequestInterceptor interceptor = new GitHubRequestInterceptor();
        AuthenticationModel model = new AuthenticationModel(interceptor, GitHubAuthServiceFactory.create(interceptor, new AuthenticationErrorHandler()));
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

