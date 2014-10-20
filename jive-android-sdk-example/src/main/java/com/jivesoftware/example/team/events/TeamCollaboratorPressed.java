package com.jivesoftware.example.team.events;

import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorPressed {
    private final User user;
    public TeamCollaboratorPressed(User user) {
        this.user = user;
    }
}
