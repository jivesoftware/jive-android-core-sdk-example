package com.jivesoftware.example.teams.events;

import com.jivesoftware.example.github.dao.Team;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamPressed {
    public final Team team;
    public TeamPressed(Team team) {
        this.team = team;
    }
}
