package com.jivesoftware.example.github;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by mark.schisler on 9/3/14.
 */
public interface IGitHubRepoService {
    @GET("/user/repos")
    Repository[] getUserRepositories() throws AuthenticationException, TwoFactorException;

    @GET("/user/repos")
    void getUserRepositories(Callback<Repository[]> callback);

    @GET("user/orgs")
    Organization[] getOrganizations() throws AuthenticationException, TwoFactorException;

    @GET("user/orgs")
    void getOrganizations(Callback<Organization[]> callback);
}
