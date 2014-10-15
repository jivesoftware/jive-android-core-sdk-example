package com.jivesoftware.example.injection;

import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.GitHubOauthRequestInterceptor;
import com.jivesoftware.example.github.GitHubServiceFactory;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.utils.PersistedKeyValueStore;
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
        return GitHubServiceFactory.createRepoService(interceptor, new AuthenticationErrorHandler());
    }

    @Provides
    public IGitHubAuthService provideGitHubAuthService(GitHubBasicAuthRequestInterceptor interceptor) {
        return GitHubServiceFactory.createAuthService(interceptor, new AuthenticationErrorHandler());
    }
}