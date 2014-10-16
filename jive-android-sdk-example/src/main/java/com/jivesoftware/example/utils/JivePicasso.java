package com.jivesoftware.example.utils;

import android.content.Context;
import com.squareup.picasso.Picasso;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class JivePicasso {
    private static final boolean DEBUG = false;
    private static volatile Picasso picasso;

    public static synchronized Picasso instance(Context context) {
        if (picasso == null) {
            Context applicationContext = context.getApplicationContext();
            Picasso.Builder builder = new Picasso.Builder(applicationContext);
            AuthenticatedDownloader authenticatedDownloader = new AuthenticatedDownloader(applicationContext, new PersistedKeyValueStore(applicationContext));
            picasso = builder.downloader(authenticatedDownloader).build();
            if (DEBUG) {
                picasso.setLoggingEnabled(true);
                picasso.setDebugging(true);
            }
        }
        return picasso;
    }
}
