package com.jivesoftware.example.utils;

import android.content.Context;
import com.jivesoftware.example.github.authentication.GitHubAuthenticationActivity;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/21/14.
 */
public class AuthorizationReset {
    private final Context context;
    private final ActivityLauncher activityLauncher;
    private final PersistedKeyValueStore persistedKeyValueStore;

    @Inject
    public AuthorizationReset(Context context, ActivityLauncher activityLauncher, PersistedKeyValueStore persistedKeyValueStore) {
        this.context = context;
        this.activityLauncher = activityLauncher;
        this.persistedKeyValueStore = persistedKeyValueStore;
    }

    public void performReset() {
        persistedKeyValueStore.clear();
        activityLauncher.launchClearTop(context, GitHubAuthenticationActivity.class);
    }

}
