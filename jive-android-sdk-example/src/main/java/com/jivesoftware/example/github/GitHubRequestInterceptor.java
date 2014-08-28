package com.jivesoftware.example.github;

import retrofit.RequestInterceptor;

import static com.jivesoftware.example.Constants.AUTHENTICATION;
import static com.jivesoftware.example.Constants.GITHUB_OTP_HEADER;

/**
* Created by mark.schisler on 8/28/14.
*/
public class GitHubRequestInterceptor implements RequestInterceptor {
    private String username;
    private String password;
    private String token;
    private String otp;

    public GitHubRequestInterceptor() {}

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(AUTHENTICATION, token);
        if (otp != null) {
            request.addHeader(GITHUB_OTP_HEADER, otp);
        }
    }

    public void setUsername(String username) {
        this.username = username;
        this.token = AuthenticationTokenFactory.create(username, password);
    }

    public void setPassword(String password) {
        this.password = password;
        this.token = AuthenticationTokenFactory.create(username, password);
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
