package com.jivesoftware.example.github.authentication;

import com.bindroid.trackable.TrackableBoolean;
import com.bindroid.trackable.TrackableField;
import com.bindroid.trackable.TrackableInt;
import com.jivesoftware.example.Constants;
import com.jivesoftware.example.R;
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

    private final TrackableBoolean authFailed = new TrackableBoolean(true);
    private final TrackableInt authMessageId = new TrackableInt();
    private final TrackableBoolean twoFactorRequired = new TrackableBoolean(false);

    private final TrackableField<String> username = new TrackableField<String>("");
    private final TrackableField<String> password = new TrackableField<String>("");
    private final TrackableField<String> onetime = new TrackableField<String>("");

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getOnetime() {
        return onetime.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setOnetime(String onetime) {
        this.onetime.set(onetime);
    }

    public void setAuthFailed(boolean authFailed) {
        this.authFailed.set(authFailed);
    }

    public boolean getAuthFailed() {
        return authFailed.get();
    }

    public void setAuthMessageId(int authMessageId) {
        this.authMessageId.set(authMessageId);
    }

    public int getAuthMessageId() {
        return authMessageId.get();
    }

    public boolean getUsernameAndPasswordAccepted() {
        return !getAuthFailed() || getTwoFactorRequired();
    }

    public void setTwoFactorRequired(boolean twoFactorRequired) {
        this.twoFactorRequired.set(twoFactorRequired);
    }

    public boolean getTwoFactorRequired() {
        return twoFactorRequired.get();
    }

    public enum Type {
        OAUTH_AUTHENTICATION_SUCCESS
    }

    @Inject
    public GitHubAuthenticationModel(GitHubBasicAuthRequestInterceptor interceptor, IGitHubAuthService gitHubAuthService, PersistedKeyValueStore keyValueStore, TypeListenable typeListenable) {
        this.listenable = typeListenable;
        this.gitHubBasicAuthRequestInterceptor = interceptor;
        this.gitHubAuthService = gitHubAuthService;
        this.keyValueStore = keyValueStore;
    }

    public void obtainBasicAuth() {
        gitHubBasicAuthRequestInterceptor.setUsername(username.get());
        gitHubBasicAuthRequestInterceptor.setPassword(password.get());
        gitHubBasicAuthRequestInterceptor.setOtp(onetime.get());

        gitHubAuthService.getSelf(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                obtainOauthToken();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getCause() instanceof TwoFactorException) {
                    GitHubAuthenticationModel.this.setAuthFailed(false);
                    GitHubAuthenticationModel.this.setTwoFactorRequired(true);
                    GitHubAuthenticationModel.this.setAuthMessageId(R.string.two_factor_required);
                } else {
                    GitHubAuthenticationModel.this.setAuthFailed(true);
                    GitHubAuthenticationModel.this.setAuthMessageId(R.string.bad_username_password);
                }
            }
        });
    }

    public void obtainOauthToken() {
        AuthorizationRequest request = new AuthorizationRequest();
        request.clientId = Constants.OAUTH_CLIENT_ID;
        request.clientSecret = Constants.OAUTH_CLIENT_SECRET;
        request.note = getClass().getName();
        request.scopes = new String[2];
        Arrays.asList("repo", "admin:org").toArray(request.scopes);

        gitHubAuthService.postAuthorization(request, new Callback<Authorization>() {
            @Override
            public void success(Authorization authorization, Response response) {
                GitHubAuthenticationModel.this.setAuthFailed(false);
                keyValueStore.putGithubToken(authorization.token);
                listenable.post(OAUTH_AUTHENTICATION_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                GitHubAuthenticationModel.this.setAuthFailed(true);
                GitHubAuthenticationModel.this.setAuthMessageId(R.string.oauth_failure);
            }
        });
    }

    public boolean doTokensExist() {
        return keyValueStore.doTokensExist();
    }
}
