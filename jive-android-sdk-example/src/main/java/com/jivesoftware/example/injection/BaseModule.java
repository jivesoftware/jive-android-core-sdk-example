package com.jivesoftware.example.injection;

import android.content.Context;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.authentication.GitHubAuthenticationErrorHandler;
import com.jivesoftware.example.github.service.IGitHubRepoService;
import com.jivesoftware.example.jive.dao.JiveConnection;
import com.jivesoftware.example.utils.JivePicasso;
import com.jivesoftware.example.utils.PersistedKeyValueStore;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

/**
 * Created by stephen.mclaughry on 9/23/14.
 */
@Module( library=true, complete=false )
public class BaseModule {
    @Provides
    public GitHubOauthRequestInterceptor provideGitHubOauthRequestInterceptor(PersistedKeyValueStore store) {
        return new GitHubOauthRequestInterceptor(store.getGithubToken());
    }

    @Provides
    public IGitHubRepoService provideGitHubRepoService(GitHubOauthRequestInterceptor interceptor) {
        return GitHubServiceFactory.createRepoService(interceptor, new GitHubAuthenticationErrorHandler());
    }

    @Provides
    public JiveConnection provideJiveConnection() {
        return JiveConnection.instance();
    }

    @Provides
    public Picasso providePicasso(Context context) {
        return JivePicasso.instance(context);
    }
}
