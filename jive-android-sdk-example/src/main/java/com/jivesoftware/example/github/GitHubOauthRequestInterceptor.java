package com.jivesoftware.example.github;

import com.google.common.net.HttpHeaders;
import retrofit.RequestInterceptor;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class GitHubOauthRequestInterceptor implements RequestInterceptor {
    private final String token;

    public GitHubOauthRequestInterceptor(String token) {this.token = token;}

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(HttpHeaders.AUTHORIZATION, "token " + token);
    }
}
