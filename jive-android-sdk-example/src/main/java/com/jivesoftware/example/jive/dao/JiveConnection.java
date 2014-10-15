package com.jivesoftware.example.jive.dao;

import com.jivesoftware.android.mobile.sdk.core.JiveCore;
import com.jivesoftware.android.mobile.sdk.core.JiveCoreUnauthenticated;
import com.jivesoftware.android.mobile.sdk.entity.PersonEntity;
import com.jivesoftware.android.mobile.sdk.entity.TokenEntity;
import com.jivesoftware.android.mobile.sdk.json.JiveJson;
import com.jivesoftware.example.utils.BackgroundLooper;
import com.jivesoftware.example.utils.BackgroundRunner;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.inject.Singleton;
import java.net.URL;

import static com.jivesoftware.example.utils.BackgroundRunner.JiveBackgroundRunnable;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/15/14.
 */
@Singleton
public class JiveConnection {
    private final JiveJson jiveJson;
    private final BackgroundRunner backgroundRunner;

    private JiveTokenProvider jiveTokenProvider;
    private JiveCore jiveCoreAuthenticated;
    private JiveCoreUnauthenticated jiveCoreUnauthenticated;

    public JiveConnection() {
        this.backgroundRunner = new BackgroundRunner(new BackgroundLooper());
        this.jiveJson = new JiveJson();
    }

    public void authenticate(final String username, final String password, final URL endpoint, JiveResultCallback<TokenEntity> callback) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        this.jiveCoreUnauthenticated = new JiveCoreUnauthenticated(endpoint, httpClient, jiveJson);
        this.jiveTokenProvider = new JiveTokenProvider(jiveCoreUnauthenticated);
        this.jiveCoreAuthenticated = new JiveCore(endpoint, httpClient,jiveTokenProvider, jiveTokenProvider, jiveJson);
        backgroundRunner.post(new JiveBackgroundRunnable<TokenEntity>() {
            @Override
            public TokenEntity run() throws Exception {
                jiveTokenProvider.setCredentials(username, password);
                return jiveCoreUnauthenticated.authorizeDevice(username, password).call();
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
}
