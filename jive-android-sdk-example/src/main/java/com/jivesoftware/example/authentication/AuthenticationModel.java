package com.jivesoftware.example.authentication;

import com.jivesoftware.example.Constants;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubBasicAuthRequestInterceptor;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.dao.Authorization;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.jivesoftware.example.authentication.AuthenticationModel.Type.BASIC_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.BASIC_AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.OAUTH_AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.OAUTH_AUTHENTICATION_SUCCESS;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class AuthenticationModel {
    public final TypeListenable listenable = new TypeListenable();
    private GitHubBasicAuthRequestInterceptor gitHubBasicAuthRequestInterceptor;
    private IGitHubAuthService gitHubAuthService;
    private String oAuthToken;

    public enum Type {
        BASIC_AUTHENTICATION_SUCCESS,
        BASIC_AUTHENTICATION_FAILURE,
        BASIC_AUTHENTICATION_TWO_FACTOR_REQUIRED,

        OAUTH_AUTHENTICATION_SUCCESS,
        OAUTH_AUTHENTICATION_FAILURE
    }

    public AuthenticationModel(GitHubBasicAuthRequestInterceptor interceptor, IGitHubAuthService gitHubAuthService) {
        this.gitHubBasicAuthRequestInterceptor = interceptor;
        this.gitHubAuthService = gitHubAuthService;
    }

    public void obtainBasicAuth() {
        gitHubAuthService.getUser(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                listenable.post(user, BASIC_AUTHENTICATION_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                if ( error.getCause() instanceof TwoFactorException) {
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
        gitHubAuthService.postAuthorization(request, new Callback<Authorization>() {
            @Override
            public void success(Authorization authorization, Response response) {
                oAuthToken = authorization.token;
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
