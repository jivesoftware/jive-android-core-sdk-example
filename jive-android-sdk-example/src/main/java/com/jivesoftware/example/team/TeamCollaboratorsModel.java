package com.jivesoftware.example.team;

import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.github.service.IGitHubRepoService;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.team.events.TeamCollaborators;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_COLLABORATORS_FAILURE;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_COLLABORATORS_SUCCESS;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_USER_DELETE_FAILURE;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_USER_DELETE_SUCCESS;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorsModel {
    public TypeListenable listenable;
    private IGitHubRepoService gitHubRepoService;
    private Team team;


    public enum Type {
        TEAM_COLLABORATORS_SUCCESS,
        TEAM_COLLABORATORS_FAILURE,
        TEAM_USER_DELETE_SUCCESS,
        TEAM_USER_DELETE_FAILURE,
    }

    @Inject
    public TeamCollaboratorsModel(IGitHubRepoService gitHubRepoService, TypeListenable listenable, Team team) {
        this.gitHubRepoService = gitHubRepoService;
        this.listenable = listenable;
        this.team = team;
    }

    public void refresh() {
        gitHubRepoService.getTeamMembers(team.id, new Callback<User[]>() {
            @Override
            public void success(User[] users, Response response) {
                listenable.post(new TeamCollaborators(users), TEAM_COLLABORATORS_SUCCESS);
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(TEAM_COLLABORATORS_FAILURE);
            }
        });
    }

    public void deleteTeamMember(User user) {
        gitHubRepoService.deleteTeamMember(team.id,user.login,new Callback<Void>() {
            @Override
            public void success(Void aVoid, Response response) {
                listenable.post(TEAM_USER_DELETE_SUCCESS);
                refresh();
            }

            @Override
            public void failure(RetrofitError error) {
                listenable.post(TEAM_USER_DELETE_FAILURE);
            }
        });
    }
}
