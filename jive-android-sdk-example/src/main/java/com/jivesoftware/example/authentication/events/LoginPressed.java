package com.jivesoftware.example.authentication.events;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class LoginPressed {
    public final String username;
    public final String password;

    public LoginPressed(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
