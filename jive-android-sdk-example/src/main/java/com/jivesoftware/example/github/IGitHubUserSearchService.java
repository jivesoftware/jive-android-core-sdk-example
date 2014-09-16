package com.jivesoftware.example.github;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.List;
import com.jivesoftware.example.github.dao.User;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by mark.schisler on 9/16/14.
 */
public interface IGitHubUserSearchService {
    @GET("/search/users")
    List<User> getUsers(@Query("q") String query) throws AuthenticationException, TwoFactorException;
}
