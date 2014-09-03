package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.jivesoftware.example.R;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class PersistedKeyValueStore {
    private Context context;

    public PersistedKeyValueStore(Context context) {
        this.context = context;
    }

    public String getGithubToken() {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.token_key),null);
    }

    public boolean putGithubToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.token_key), token);
        return editor.commit();
    }
}
