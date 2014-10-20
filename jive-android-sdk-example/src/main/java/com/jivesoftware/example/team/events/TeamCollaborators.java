package com.jivesoftware.example.team.events;

import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaborators {
    public final User[] users;
    public TeamCollaborators(User[] users) {
        this.users = users;
    }
}
