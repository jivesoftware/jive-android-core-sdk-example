package com.jivesoftware.example.github;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jivesoftware.example.authentication.AuthenticationErrorHandler;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import java.util.Date;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class GitHubAuthServiceFactory {
    public static IGitHubAuthService create(GitHubRequestInterceptor gitHubRequestInterceptor, AuthenticationErrorHandler authErrorHandler) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.github.com")
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(gitHubRequestInterceptor)
                .setErrorHandler(authErrorHandler)
                .build();

        return restAdapter.create(IGitHubAuthService.class);
    }

}
