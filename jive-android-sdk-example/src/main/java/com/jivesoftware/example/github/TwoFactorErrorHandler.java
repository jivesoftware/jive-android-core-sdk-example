package com.jivesoftware.example.github;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class TwoFactorErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response response = cause.getResponse();
        if ( response != null ) {
            List<Header> headers = response.getHeaders();
            if ( headers != null ) {
                for ( Header header : headers) {
                    if ( "X-GitHub-OTP".equals(header.getName()) ) {
                        return new TwoFactorException();
                    }
                }
            }
        }
        return cause;
    }
}
