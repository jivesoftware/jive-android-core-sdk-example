package com.jivesoftware.example.jive.dao;

import com.jivesoftware.android.mobile.sdk.core.JiveCoreCallable;
import com.jivesoftware.android.mobile.sdk.core.JiveCoreTokenEntityRefresher;
import com.jivesoftware.android.mobile.sdk.core.JiveCoreTokenEntityStore;
import com.jivesoftware.android.mobile.sdk.core.JiveCoreUnauthenticated;
import com.jivesoftware.android.mobile.sdk.entity.TokenEntity;
import com.jivesoftware.example.utils.PersistedKeyValueStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveTokenProvider implements JiveCoreTokenEntityStore, JiveCoreTokenEntityRefresher {
    private TokenEntity tokenEntity;
    private final PersistedKeyValueStore keyValueStore;
    private JiveCoreUnauthenticated jiveCoreUnauthenticated;
    private String username;
    private String password;

    public JiveTokenProvider(PersistedKeyValueStore keyValueStore, JiveCoreUnauthenticated jiveCoreUnauthenticated) {
        this.keyValueStore = keyValueStore;
        this.jiveCoreUnauthenticated = jiveCoreUnauthenticated;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Nullable
    @Override
    public TokenEntity getTokenEntity() throws IOException {
        tokenEntity = keyValueStore.getTokenEntity();
        if ( tokenEntity == null ) {
            JiveCoreCallable<TokenEntity> authorizeDeviceCallable = jiveCoreUnauthenticated.authorizeDevice(username, password);
            this.tokenEntity = authorizeDeviceCallable.call();
        }
        return tokenEntity;
    }

    @Nullable
    @Override
    public TokenEntity refreshTokenEntity(@Nonnull String refreshToken) throws IOException {
        JiveCoreCallable<TokenEntity> refreshTokenCallable = jiveCoreUnauthenticated.refreshToken(refreshToken);
        tokenEntity = refreshTokenCallable.call();
        keyValueStore.putTokenEntity(tokenEntity);
        return tokenEntity;
    }
}
