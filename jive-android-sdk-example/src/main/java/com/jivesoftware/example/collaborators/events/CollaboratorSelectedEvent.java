package com.jivesoftware.example.collaborators.events;

import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class CollaboratorSelectedEvent {
    public final User user;
    public CollaboratorSelectedEvent(User user) {
        this.user = user;
    }

}
