package com.jivesoftware.example.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.google.common.net.HttpHeaders;
import com.squareup.picasso.UrlConnectionDownloader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class AuthenticatedDownloader extends UrlConnectionDownloader {
    private PersistedKeyValueStore keyValueStore;

    public AuthenticatedDownloader(Context context, PersistedKeyValueStore keyValueStore) {
        super(context);
        this.keyValueStore = keyValueStore;
    }

    @Override
    protected HttpURLConnection openConnection(Uri path) throws IOException {
        HttpURLConnection connection = super.openConnection(path);
        BasicNameValuePair header = new BasicNameValuePair(HttpHeaders.AUTHORIZATION, keyValueStore.getJiveToken());
        if (header != null) {
            connection.addRequestProperty(header.getName(), header.getValue());
        }
        Log.wtf("WTF",String.format("SENDING HEADER %s => %s",header.getName(), header.getValue()));
        return connection;
    }
}
