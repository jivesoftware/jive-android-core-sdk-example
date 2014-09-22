package com.jivesoftware.example.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class URLUtils {
    public static String getPath(String url) {
        try {
            return new URL(url).getPath().substring(1);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
