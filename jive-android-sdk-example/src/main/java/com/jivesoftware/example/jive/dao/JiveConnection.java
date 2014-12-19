package com.jivesoftware.example.jive.dao;

import android.util.Log;
import com.jivesoftware.android.mobile.sdk.core.JiveCore;
import com.jivesoftware.android.mobile.sdk.core.JiveCoreUnauthenticated;
import com.jivesoftware.android.mobile.sdk.entity.PersonEntity;
import com.jivesoftware.android.mobile.sdk.entity.PersonListEntity;
import com.jivesoftware.android.mobile.sdk.entity.TokenEntity;
import com.jivesoftware.android.mobile.sdk.json.JiveJson;
import com.jivesoftware.example.Constants;
import com.jivesoftware.example.destroyer.IDestroyable;
import com.jivesoftware.example.utils.BackgroundRunner;
import com.jivesoftware.example.utils.BackgroundThread;
import com.jivesoftware.example.utils.PersistedKeyValueStore;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.inject.Inject;
import java.net.URL;

import static com.jivesoftware.example.utils.BackgroundRunner.JiveBackgroundRunnable;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveConnection implements IDestroyable {
    private final String TAG = getClass().getName();
    private final JiveJson jiveJson;
    private final BackgroundRunner backgroundRunner;
    private final PersistedKeyValueStore keyValueStore;

    private JiveTokenProvider jiveTokenProvider;
    private JiveCore jiveCoreAuthenticated;
    private JiveCoreUnauthenticated jiveCoreUnauthenticated;
    private DefaultHttpClient httpClientUnauthenticated;
    private DefaultHttpClient httpClientAuthenticated;

    @Inject
    public JiveConnection(PersistedKeyValueStore keyValueStore) {
        this.keyValueStore = keyValueStore;
        this.backgroundRunner = new BackgroundRunner(new BackgroundThread());
        this.jiveJson = new JiveJson();
        updateFromPersistedStore(keyValueStore);
    }

    @Override
    public void destroy() {
        backgroundRunner.destroy();
        httpClientAuthenticated.getConnectionManager().shutdown();
        httpClientUnauthenticated.getConnectionManager().shutdown();
    }

    private void setEndpoint(final URL endpoint) {
        this.httpClientUnauthenticated = new DefaultHttpClient();
        this.jiveCoreUnauthenticated = new JiveCoreUnauthenticated(endpoint, Constants.OAUTH_CREDENTIALS, Constants.OAUTH_ADDON_UUID, httpClientUnauthenticated, jiveJson);
        this.jiveTokenProvider = new JiveTokenProvider(keyValueStore, jiveCoreUnauthenticated);
        this.httpClientAuthenticated = new DefaultHttpClient();
        this.jiveCoreAuthenticated = new JiveCore(endpoint, Constants.OAUTH_CREDENTIALS, httpClientAuthenticated,jiveTokenProvider, jiveTokenProvider, jiveJson);
    }

    public void authenticate(final String username, final String password, final URL endpoint, JiveResultCallback<TokenEntity> callback) {
        setEndpoint(endpoint);
        backgroundRunner.post(new JiveBackgroundRunnable<TokenEntity>() {
            @Override
            public TokenEntity run() throws Exception {
                jiveTokenProvider.setCredentials(username, password);
                TokenEntity entity = jiveCoreUnauthenticated.authorizeDevice(username, password).call();
                keyValueStore.putTokenEntity(entity);
                return entity;
            }
        }, callback);
    }

    public void fetchMePerson(JiveResultCallback<PersonEntity> callback) {
        backgroundRunner.post(new JiveBackgroundRunnable<PersonEntity>() {
            @Override
            public PersonEntity run() throws Exception {
                return jiveCoreAuthenticated.fetchMePerson().call();
            }
        }, callback);
    }

    public void fetchPerson(final String pathAndQueryString, JiveResultCallback<PersonEntity> callback) {
        backgroundRunner.post(new JiveBackgroundRunnable<PersonEntity>() {
            @Override
            public PersonEntity run() throws Exception {
                return jiveCoreAuthenticated.fetchPerson(pathAndQueryString).call();
            }
        }, callback);
    }

    public void fetchFollowing(final String pathAndQueryString, JiveResultCallback<PersonListEntity> callback) {
        backgroundRunner.post(new JiveBackgroundRunnable<PersonListEntity>() {
            @Override
            public PersonListEntity run() throws Exception {
                return jiveCoreAuthenticated.fetchList(pathAndQueryString, PersonListEntity.class).call();
            }
        }, callback);
    }

    private void updateFromPersistedStore(PersistedKeyValueStore store) {
        String endpoint = store.getEndpoint();
        try {
            setEndpoint(new URL(endpoint));
        } catch (Exception e) {
            Log.w(TAG, "Failed to update endpoint", e);
        }
    }
}
