package com.jivesoftware.example.jive.dao;

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
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URL;

import static com.jivesoftware.example.utils.BackgroundRunner.JiveBackgroundRunnable;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveConnection implements IDestroyable {
    private final JiveJson jiveJson;
    private final BackgroundRunner backgroundRunner;

    private JiveTokenProvider jiveTokenProvider;
    private JiveCore jiveCoreAuthenticated;
    private JiveCoreUnauthenticated jiveCoreUnauthenticated;
    private DefaultHttpClient httpClientUnauthenticated;
    private DefaultHttpClient httpClientAuthenticated;
    private volatile static JiveConnection instance;

    public JiveConnection() {
        this.backgroundRunner = new BackgroundRunner(new BackgroundThread());
        this.jiveJson = new JiveJson();
    }

    @Override
    public void destroy() {
        backgroundRunner.destroy();
        httpClientAuthenticated.getConnectionManager().shutdown();
        httpClientUnauthenticated.getConnectionManager().shutdown();
    }

    public void authenticate(final String username, final String password, final URL endpoint, JiveResultCallback<TokenEntity> callback) {
        httpClientUnauthenticated = new DefaultHttpClient();
        this.jiveCoreUnauthenticated = new JiveCoreUnauthenticated(endpoint, Constants.OAUTH_CREDENTIALS, Constants.OAUTH_ADDON_UUID, httpClientUnauthenticated, jiveJson);
        this.jiveTokenProvider = new JiveTokenProvider(jiveCoreUnauthenticated);
        httpClientAuthenticated = new DefaultHttpClient();
        this.jiveCoreAuthenticated = new JiveCore(endpoint, httpClientAuthenticated,jiveTokenProvider, jiveTokenProvider, jiveJson);
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

    public void fetchFollowing(final String pathAndQueryString, JiveResultCallback<PersonListEntity> callback) {
        backgroundRunner.post(new JiveBackgroundRunnable<PersonListEntity>() {
            @Override
            public PersonListEntity run() throws Exception {
                return jiveCoreAuthenticated.fetchList(pathAndQueryString, PersonListEntity.class).call();
            }
        }, callback);
    }

    public static JiveConnection instance() {
        if ( instance == null ) {
            instance = new JiveConnection();
        }
        return instance;
    }

}
