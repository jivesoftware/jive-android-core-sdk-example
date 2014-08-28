package com.jivesoftware.example.github;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.User;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by mark.schisler on 8/26/14.
 */
public interface IGitHubAuthService {
    @GET("/user")
    User getUser() throws AuthenticationException, TwoFactorException;

    @GET("/user")
    void getUser(Callback<User> callback);
}
