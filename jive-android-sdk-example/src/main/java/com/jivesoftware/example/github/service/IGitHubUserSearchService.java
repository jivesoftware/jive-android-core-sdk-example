package com.jivesoftware.example.github.service;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.GitHubList;
import com.jivesoftware.example.github.dao.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by mark.schisler on 9/16/14.
 */
public interface IGitHubUserSearchService {
    @GET("/search/users")
    GitHubList<User> getUsers(@Query("q") String query) throws AuthenticationException, TwoFactorException;

    @GET("/search/users")
    void getUsers(@Query("q") String query, Callback<GitHubList<User>> callback);

}
