package com.jivesoftware.example.teams;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jivesoftware.example.github.dao.Team;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsAdapter extends BaseAdapter {
    private Team[] teams = new Team[0];
    private Context context;

    public TeamsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.teams.length;
    }

    @Override
    public Object getItem(int position) {
        return this.teams[position];
    }

    @Override
    public long getItemId(int position) {
        return this.teams[position].id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeamView teamView = (TeamView) convertView;
        if ( teamView == null ) {
            teamView = new TeamView(this.context);
        }
        teamView.refresh((Team) getItem(position));
        return teamView;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }
}
