package com.jivesoftware.example.followers.events;

import java.util.List;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersUpdate {
    public List<String> names;
    public FollowersUpdate(List<String> names) {
        this.names = names;
    }
}
