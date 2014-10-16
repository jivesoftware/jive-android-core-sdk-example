package com.jivesoftware.example.collaborators.events;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class CollaboratorDeleteEvent {
    public final long id;
    public CollaboratorDeleteEvent(long id) {
        this.id = id;
    }
}
