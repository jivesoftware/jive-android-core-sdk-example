package com.jivesoftware.example.github.authentication;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import java.util.List;

import static com.jivesoftware.example.Constants.AUTHENTICATION_ERROR_CODE;
import static com.jivesoftware.example.Constants.GITHUB_OTP_HEADER;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class GitHubAuthenticationErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause.getResponse() != null && cause.getResponse().getStatus() == AUTHENTICATION_ERROR_CODE) {
            Response response = cause.getResponse();
            if ( response != null ) {
                List<Header> headers = response.getHeaders();
                if ( headers != null ) {
                    for ( Header header : headers) {
                        if ( GITHUB_OTP_HEADER.equals(header.getName()) ) {
                            return new TwoFactorException();
                        }
                    }
                }
            }
            return new AuthenticationException();
        }
        return cause;
    }
}
