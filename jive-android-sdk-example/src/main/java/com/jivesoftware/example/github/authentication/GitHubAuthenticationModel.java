package com.jivesoftware.example.github.authentication;

import com.jivesoftware.example.Constants;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.service.IGitHubAuthService;
import com.jivesoftware.example.github.dao.Authorization;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.PersistedKeyValueStore;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.OAUTH_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.github.authentication.GitHubAuthenticationModel.Type.OAUTH_AUTHENTICATION_SUCCESS;

/**
 * Created by mark.schisler on 8/28/14.
 */
@Singleton
public class GitHubAuthenticationModel {
    public final TypeListenable listenable;
    private final GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor;
    private final IGitHubAuthService gitHubAuthService;
    private final PersistedKeyValueStore keyValueStore;

    public enum Type {
        BASIC_AUTHENTICATION_SUCCESS,
        BASIC_AUTHENTICATION_FAILURE,
        BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED,

        OAUTH_AUTHENTICATION_SUCCESS,
        OAUTH_AUTHENTICATION_FAILURE
    }

    @Inject
    public GitHubAuthenticationModel(GitHubBasicAuthRequestInterceptor interceptor, IGitHubAuthService gitHubAuthService, PersistedKeyValueStore keyValueStore, TypeListenable typeListenable) {
        this.listenable = typeListenable;
        this.gitHubBasicAuthRequestInterceptor = interceptor;
        this.gitHubAuthService = gitHubAuthService;
        this.keyValueStore = keyValueStore;
    }

    public void obtainBasicAuth() {
        gitHubAuthService.getSelf(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                listenable.post(user, BASIC_AUTHENTICATION_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getCause() instanceof TwoFactorException) {
                    listenable.post(BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED);
                } else {
                    listenable.post(BASIC_AUTHENTICATION_FAILURE);
                }
            }
        });
    }

    public void obtainOauthToken() {
        AuthorizationRequest request = new AuthorizationRequest();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        request.note = getClass().getName();
        request.scopes = new String [2];
        Arrays.asList("repo", "admin:org").toArray(request.scopes);

        gitHubAuthService.postAuthorization(request, new Callback<Authorization>() {
            @Override
            public void success(Authorization authorization, Response response) {
                keyValueStore.putGithubToken(authorization.token);
                listenable.post(OAUTH_AUTHENTICATION_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(OAUTH_AUTHENTICATION_FAILURE);
            }
        });
    }

    public void setUsername(String username) {
        gitHubBasicAuthRequestInterceptor.setUsername(username);
    }

    public void setPassword(String password) {
        gitHubBasicAuthRequestInterceptor.setPassword(password);
    }

    public void setOnetime(String onetime) {
        gitHubBasicAuthRequestInterceptor.setOtp(onetime);
    }
}
