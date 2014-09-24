package com.jivesoftware.example.teams;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsView extends LinearLayout {
    private final TeamsAdapter adapter;

    @InjectView(R.id.team_listview)
    ListView listView;

    @Inject
    public TeamsView(Context context, TeamsAdapter teamsAdapter) {
        super(context);
        View view = inflate(context, R.layout.teams, this);
        ButterKnife.inject(this, view);

        adapter = teamsAdapter;

        listView.setAdapter(adapter);
    }

    public void setTeams(Team[] repositories) {
        adapter.setTeams(repositories);
    }
}
