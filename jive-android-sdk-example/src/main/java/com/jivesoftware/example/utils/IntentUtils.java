package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.Intent;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.teams.TeamsActivity;
import com.jivesoftware.example.users.CollaboratorActivity;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class IntentUtils {
    public static void startTeamActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, TeamsActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }

    public static void startCollaboratorActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, CollaboratorActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }
}
