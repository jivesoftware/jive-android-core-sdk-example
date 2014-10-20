package com.jivesoftware.example.team.events;

import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorLongPressed {
    public final User user;
    public TeamCollaboratorLongPressed(User user) {
        this.user = user;
    }
}
