package com.jivesoftware.example.github;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.Authorization;
import com.jivesoftware.example.github.dao.AuthorizationRequest;
import com.jivesoftware.example.github.dao.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by mark.schisler on 8/26/14.
 */
public interface IGitHubAuthService {
    @GET("/user")
    User getSelf() throws AuthenticationException, TwoFactorException;

    @GET("/user")
    void getSelf(Callback<User> callback);

    @POST("/authorizations")
    Authorization postAuthorization(@Body AuthorizationRequest request) throws AuthenticationException, TwoFactorException;

    @POST("/authorizations")
    void postAuthorization(@Body AuthorizationRequest request, Callback<Authorization> callback);

    @DELETE("/authorizations/{id}")
    Void deleteAuthorization(@Path("id") int id) throws AuthenticationException, TwoFactorException;

    @DELETE("/authorizations/{id}")
    void deleteAuthorization(@Path("id") int id, Callback<Void> callback);
}
