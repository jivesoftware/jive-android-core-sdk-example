package com.jivesoftware.example.teams;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.teams.events.TeamPressed;
import com.jivesoftware.example.utils.IntentUtils;

import static com.jivesoftware.example.teams.TeamsView.Type.TEAM_PRESSED;

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

        view.listenable.setListener(new IValueListener<TeamPressed>() {
            @Override
            public void onPost(TeamPressed event) {
                IntentUtils.startTeamActivity(activity, event.team);
            }
        }, TEAM_PRESSED);

        model.refresh();
        activity.getActionBar().setTitle(activity.getString(R.string.teams));
        activity.setContentView(view);
    }
}
