package com.jivesoftware.example.teams;

import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.URLUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.jivesoftware.example.teams.TeamsModel.Type.TEAM_REFRESH_FAILURE;
import static com.jivesoftware.example.teams.TeamsModel.Type.TEAM_REFRESH_SUCCESS;

/**
 * Created by mark.schisler on 9/22/14.
 */
@Singleton
public class TeamsModel {
    public final TypeListenable listenable;
    private final IGitHubRepoService repoService;
    private final Repository repository;

    enum Type {
        TEAM_REFRESH_SUCCESS,
        TEAM_REFRESH_FAILURE
    }

    @Inject
    public TeamsModel(IGitHubRepoService repoService, Repository repository, TypeListenable typeListenable) {
        this.repoService = repoService;
        this.repository = repository;
        this.listenable = typeListenable;
    }

    public void refresh() {
        this.repoService.getRepoTeams(URLUtils.getPath(repository.url),new Callback<Team[]>() {
            @Override
            public void success(Team[] teams, Response response) {
                listenable.post(teams, TEAM_REFRESH_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(TEAM_REFRESH_FAILURE);
            }
        });
    }

}
