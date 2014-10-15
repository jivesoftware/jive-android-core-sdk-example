package com.jivesoftware.example.jive.authentication.events;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class JiveLoginPressed {
    public final String username;
    public final String password;
    public final String endpoint;

    public JiveLoginPressed(String endpoint, String username, String password) {
        this.username = username;
        this.password = password;
        this.endpoint = endpoint;
    }
}
