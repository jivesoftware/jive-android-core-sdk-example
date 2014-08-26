package com.jivesoftware.example.github;

import org.apache.http.Header;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;

import java.io.UnsupportedEncodingException;

/**
 * Created by mark.schisler on 8/26/14.
 */
public class AuthenticationTokenFactory {
    public static String create(String username, String password) throws UnsupportedEncodingException {
        Credentials credentials = new UsernamePasswordCredentials(username, password);
        Header authenticate = BasicScheme.authenticate(credentials, "IOS-8859-1", false);
        return authenticate.getValue();
    }
}
