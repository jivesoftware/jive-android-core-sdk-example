package com.jivesoftware.example.github;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class GitHubAuthServiceFactory {
    public static IGitHubAuthService create(String username, String password, final String otp, AuthenticationErrorHandler twoFactorErrorHandler) {
        try {
            final String token = AuthenticationTokenFactory.create(username, password);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("https://api.github.com")
                    .setConverter(new GsonConverter(gson))
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", token);
                            if (otp != null) {
                                request.addHeader("Authorization", token);
                            }
                        }
                    })
                    .setErrorHandler(twoFactorErrorHandler)
                    .build();

            return restAdapter.create(IGitHubAuthService.class);

        } catch (UnsupportedEncodingException e) {

            return null;

        }
    }
}
