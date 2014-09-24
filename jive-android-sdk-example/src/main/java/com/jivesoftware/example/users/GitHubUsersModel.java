package com.jivesoftware.example.users;

import com.jivesoftware.example.github.IGitHubUserSearchService;
import com.jivesoftware.example.github.dao.GitHubList;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.Joiner;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

import static com.jivesoftware.example.users.GitHubUsersModel.Type.USERS_REFRESH_FAILURE;
import static com.jivesoftware.example.users.GitHubUsersModel.Type.USERS_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/16/14.
 */
public class GitHubUsersModel {
    public final TypeListenable listenable;
    private final IGitHubUserSearchService searchService;

    public enum Type {
        USERS_REFRESH_SUCCESS,
        USERS_REFRESH_FAILURE,
    }

    public GitHubUsersModel(IGitHubUserSearchService searchService, TypeListenable typeListenable) {
        this.searchService = searchService;
        this.listenable = typeListenable;
    }

    public void refresh(String fullName) {
        String query = "fullName:" + fullName;
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
            queries.add("fullName:" + fullName);
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
