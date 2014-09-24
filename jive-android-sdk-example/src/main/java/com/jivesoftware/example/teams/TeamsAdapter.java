package com.jivesoftware.example.teams;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jivesoftware.example.github.dao.Team;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by mark.schisler on 9/22/14.
 */
@Singleton
public class TeamsAdapter extends BaseAdapter {
    private Team[] teams = new Team[0];
    private final Provider<TeamView> teamViewProvider;

    @Inject
    public TeamsAdapter(Provider<TeamView> teamViewProvider) {
        this.teamViewProvider = teamViewProvider;
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
            teamView = teamViewProvider.get();
        }
        teamView.refresh((Team) getItem(position));
        return teamView;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }
}
