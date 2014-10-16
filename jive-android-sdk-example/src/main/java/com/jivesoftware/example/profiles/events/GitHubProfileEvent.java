package com.jivesoftware.example.profiles.events;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class GitHubProfileEvent {
    public final String name;
    public final String avatarUrl;

    public GitHubProfileEvent(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
}
