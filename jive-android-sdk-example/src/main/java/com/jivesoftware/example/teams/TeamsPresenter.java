package com.jivesoftware.example.teams;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.listenable.IValueListener;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsPresenter {
    public static void create(final Activity activity, final TeamsModel model, final TeamsView view) {
        model.listenable.setListener(new IValueListener<Team[]>() {
            @Override
            public void onPost(Team[] teams) {
                view.setTeams(teams);
            }
        }, TeamsModel.Type.TEAM_REFRESH_SUCCESS);

        model.refresh();
        activity.getActionBar().setTitle(activity.getString(R.string.teams));
        activity.setContentView(view);
    }
}
