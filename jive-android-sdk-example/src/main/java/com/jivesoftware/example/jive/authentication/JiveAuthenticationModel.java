package com.jivesoftware.example.jive.authentication;

import com.jivesoftware.android.mobile.sdk.entity.TokenEntity;
import com.jivesoftware.example.jive.dao.JiveConnection;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.PersistedKeyValueStore;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jivesoftware.example.jive.authentication.JiveAuthenticationModel.Type.AUTH_FAILURE;
import static com.jivesoftware.example.jive.authentication.JiveAuthenticationModel.Type.AUTH_SUCCESS;
import static com.jivesoftware.example.utils.BackgroundRunner.JiveResultCallback;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveAuthenticationModel {
    public TypeListenable listenable;
    private String username;
    private String password;
    private URL endpoint;
    private JiveConnection connection;
    private PersistedKeyValueStore keyValueStore;

    public enum Type {
        ENDPOINT_MALFORMED,
        AUTH_SUCCESS,
        AUTH_FAILURE
    }

    @Inject
    public JiveAuthenticationModel(TypeListenable listenable, JiveConnection connection, PersistedKeyValueStore keyValueStore) {
        this.listenable = listenable;
        this.connection = connection;
        this.keyValueStore = keyValueStore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEndpoint(String endpoint) {
        try {
            this.endpoint = new URL(endpoint);
        } catch ( MalformedURLException malformedException ) {
            listenable.post(Type.ENDPOINT_MALFORMED);
        }
    }

    public void obtainAuth() {
        connection.authenticate(username, password, endpoint, new JiveResultCallback<TokenEntity>() {
            @Override
            public void success(TokenEntity tokenEntity) {
                keyValueStore.putJiveToken(tokenEntity.tokenType + " " + tokenEntity.accessToken);
                listenable.post(AUTH_SUCCESS);
            }

            @Override
            public void failure() {
                listenable.post(AUTH_FAILURE);
            }
        });
    }
}
