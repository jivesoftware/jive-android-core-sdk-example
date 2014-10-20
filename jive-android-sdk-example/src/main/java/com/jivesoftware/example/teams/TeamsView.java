package com.jivesoftware.example.teams;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.teams.events.TeamPressed;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsView extends LinearLayout {
    public TypeListenable listenable;
    @InjectView(R.id.team_listview) ListView listView;
    private final TeamsAdapter adapter;

    public enum Type {
        TEAM_PRESSED
    }

    @Inject
    public TeamsView(Context context, TeamsAdapter teamsAdapter, final TypeListenable listenable) {
        super(context);
        View view = inflate(context, R.layout.teams, this);
        ButterKnife.inject(this, view);
        this.adapter = teamsAdapter;
        this.listenable = listenable;

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) adapter.getItem(position);
                listenable.post(new TeamPressed(team), Type.TEAM_PRESSED);
            }
        });
    }

    public void setTeams(Team[] teams) {
        adapter.setTeams(teams);
    }
}
