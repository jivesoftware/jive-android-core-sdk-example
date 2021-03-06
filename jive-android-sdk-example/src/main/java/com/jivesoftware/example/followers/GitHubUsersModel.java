package com.jivesoftware.example.followers;

import com.jivesoftware.example.github.service.IGitHubUserService;
import com.jivesoftware.example.github.dao.GitHubList;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.Joiner;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_FAILURE;
import static com.jivesoftware.example.followers.GitHubUsersModel.Type.USERS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/16/14.
 */
public class GitHubUsersModel {
    public final TypeListenable listenable;
    private final IGitHubUserService searchService;

    public enum Type {
        USERS_REFRESH_SUCCESS,
        USERS_REFRESH_FAILURE,
    }

    @Inject
    public GitHubUsersModel(IGitHubUserService searchService, TypeListenable typeListenable) {
        this.searchService = searchService;
        this.listenable = typeListenable;
    }

    public void refresh(String fullName) {
        String query = "fullname:\"" + fullName + "\"";
        searchService.getUsers(query, new Callback<GitHubList<User>>() {
            @Override
            public void success(GitHubList<User> userList, Response response) {
                listenable.post(userList.items, USERS_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(USERS_REFRESH_FAILURE);
            }
        });
    }

    public void refresh(List<String> fullNames) {
        ArrayList<String> queries = new ArrayList<String>();
        for ( String fullName : fullNames) {
            queries.add("fullname:\"" + fullName + "\"");
        }
        String query = Joiner.on(" ").join(queries);
        searchService.getUsers(query, new Callback<GitHubList<User>>() {
            @Override
            public void success(GitHubList<User> userList, Response response) {
                listenable.post(userList.items, USERS_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(USERS_REFRESH_FAILURE);
            }
        });
    }
}
