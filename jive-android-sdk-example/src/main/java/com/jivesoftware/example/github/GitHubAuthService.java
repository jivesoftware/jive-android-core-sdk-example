package com.jivesoftware.example.github;

import com.jivesoftware.example.github.dao.User;
import retrofit.http.GET;

/**
 * Created by mark.schisler on 8/26/14.
 */
public interface GitHubAuthService {
    @GET("/user")
    User getUser() throws TwoFactorException;


}
