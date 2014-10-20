package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.Intent;
import com.jivesoftware.example.collaborators.CollaboratorActivity;
import com.jivesoftware.example.followers.FollowersActivity;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.team.TeamCollaboratorsActivity;
import com.jivesoftware.example.teams.TeamsActivity;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class IntentUtils {
    public static void startTeamsActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, TeamsActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }

    public static void startCollaboratorActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, CollaboratorActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }

    public static void startFollowerActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, FollowersActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }

    public static void startFollowerActivity(Context context, Team team) {
        Intent intent = new Intent(context, FollowersActivity.class);
        intent.putExtra(IntentExtraNames.TEAM, team);
        context.startActivity(intent);
    }

    public static void startTeamActivity(Context context, Team team) {
        Intent intent = new Intent(context, TeamCollaboratorsActivity.class);
        intent.putExtra(IntentExtraNames.TEAM, team);
        context.startActivity(intent);
    }
}
