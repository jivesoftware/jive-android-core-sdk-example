package com.jivesoftware.example.followers.events;

import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class FollowerSelected {
    public final User user;
    public FollowerSelected(User user) {
        this.user = user;
    }
}
