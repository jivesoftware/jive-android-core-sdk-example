package com.jivesoftware.example.github.authentication.events;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class GitHubLoginPressed {
    public final String username;
    public final String password;
    public final String onetime;

    public GitHubLoginPressed(String username, String password, String onetime) {
        this.username = username;
        this.password = password;
        this.onetime = onetime;
    }
}
