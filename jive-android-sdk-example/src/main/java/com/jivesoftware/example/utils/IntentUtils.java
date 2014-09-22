package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.Intent;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.teams.TeamsActivity;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class IntentUtils {
    public static void startTeamActivity(Context context, Repository repository) {
        Intent intent = new Intent(context, TeamsActivity.class);
        intent.putExtra(IntentExtraNames.REPOSITORY, repository);
        context.startActivity(intent);
    }
}
