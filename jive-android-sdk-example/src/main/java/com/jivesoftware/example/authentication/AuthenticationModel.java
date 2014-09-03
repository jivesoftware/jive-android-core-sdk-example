package com.jivesoftware.example.authentication;

import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.GitHubRequestInterceptor;
import com.jivesoftware.example.github.IGitHubAuthService;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_FAILURE;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_SUCCESS;
import static com.jivesoftware.example.authentication.AuthenticationModel.Type.AUTHENTICATION_TWO_FACTOR_REQUIRED;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class AuthenticationModel {
    public final TypeListenable listenable = new TypeListenable();
    private GitHubRequestInterceptor gitHubRequestInterceptor;
    private IGitHubAuthService gitHubAuthService;

    public enum Type {
        AUTHENTICATION_SUCCESS,
        AUTHENTICATION_FAILURE,
        AUTHENTICATION_TWO_FACTOR_REQUIRED
    }

    public AuthenticationModel(GitHubRequestInterceptor interceptor, IGitHubAuthService gitHubAuthService) {
        this.gitHubRequestInterceptor = interceptor;
        this.gitHubAuthService = gitHubAuthService;
    }

    public void refresh() {
        gitHubAuthService.getUser(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                listenable.post(user, AUTHENTICATION_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                if ( error.getCause() instanceof TwoFactorException) {
                    listenable.post(AUTHENTICATION_TWO_FACTOR_REQUIRED);
                } else {
                    listenable.post(AUTHENTICATION_FAILURE);
                }
            }
        });
    }

    public void setUsername(String username) {
        gitHubRequestInterceptor.setUsername(username);
    }

    public void setPassword(String password) {
        gitHubRequestInterceptor.setPassword(password);
    }

    public void setOnetime(String onetime) {
        gitHubRequestInterceptor.setOtp(onetime);
    }

}
