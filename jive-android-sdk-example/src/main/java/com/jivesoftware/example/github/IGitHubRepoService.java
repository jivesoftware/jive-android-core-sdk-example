package com.jivesoftware.example.github;

import com.jivesoftware.example.exceptions.AuthenticationException;
import com.jivesoftware.example.exceptions.TwoFactorException;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.github.dao.User;
import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by mark.schisler on 9/3/14.
 */
public interface IGitHubRepoService {
    /**/

    @GET("/user/repos")
    Repository[] getUserRepositories() throws AuthenticationException, TwoFactorException;

    @GET("/user/repos")
    void getUserRepositories(Callback<Repository[]> callback);

    @GET("/{repo_url}")
    Repository[] getRepositories(@EncodedPath("repo_url") String repoUrl);

    @GET("/{repo_url}")
    void getRepositories(@EncodedPath("repo_url") String repoUrl, Callback<Repository[]> callback);

    /**/

    @GET("/user/orgs")
    Organization[] getOrganizations() throws AuthenticationException, TwoFactorException;

    @GET("/user/orgs")
    void getOrganizations(Callback<Organization[]> callback);

    @GET("/orgs/{org}/teams")
    Team[] getOrganizationTeams(@EncodedPath("org") String name);

    @GET("/orgs/{org}/teams")
    void getOrganizationTeams(@EncodedPath("org") String name, Callback<Team[]> callback);

    /**/

    @GET("/{repo_url}/teams")
    User[] getRepoTeams(@EncodedPath("repo_url") String repoUrl);

    @GET("/{repo_url}/teams")
    void getRepoTeams(@EncodedPath("repo_url") String repoUrl, Callback<User[]> callback);

    /**/

    @GET("/{repo_url}/collaborators")
    User[] getCollaborators(@EncodedPath("repo_url") String repoUrl);

    @GET("/{repo_url}/collaborators")
    void getCollaborators(@EncodedPath("repo_url") String repoUrl, Callback<User[]> callback);

    @PUT("/{repo_url}/collaborators/{collaborator}")
    Void putCollaborator(@EncodedPath("repo_url") String repoUrl, @Path("collaborator") String collaborator);

    @PUT("/{repo_url}/collaborators/{collaborator}")
    void putCollaborator(@EncodedPath("repo_url") String repoUrl, @Path("collaborator") String collaborator, Callback<Void> callback);

    /**/

    @PUT("/teams/{id}/members/{username}")
    Void putTeamMember(@Path("id") int id, @Path("username") String username);

    @PUT("/teams/{id}/members/{username}")
    Void putTeamMember(@Path("id") int id, @Path("username") String username, Callback<Void> callback);

}
