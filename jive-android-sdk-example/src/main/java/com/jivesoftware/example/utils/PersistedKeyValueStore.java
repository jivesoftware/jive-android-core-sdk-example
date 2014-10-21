package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.jivesoftware.android.mobile.sdk.entity.TokenEntity;
import com.jivesoftware.android.mobile.sdk.json.JiveJson;
import com.jivesoftware.example.R;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * Created by mark.schisler on 9/3/14.
 */
@Singleton
public class PersistedKeyValueStore {
    private Context context;

    @Inject
    public PersistedKeyValueStore(Context context) {
        this.context = context;
    }

    public String getGithubToken() {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.github_token_key),null);
    }

    public boolean putGithubToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.github_token_key), token);
        return editor.commit();
    }

    public String getJiveToken() {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.jive_token_key),null);
    }

    public boolean putJiveToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.jive_token_key), token);
        return editor.commit();
    }

    public String getEndpoint() {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.jive_endpoint_key),null);
    }

    public boolean putEndpoint(String endpoint) {
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.jive_endpoint_key), endpoint);
        return editor.commit();
    }

    public TokenEntity getTokenEntity() {
        JiveJson json = new JiveJson();
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        try {
            String string = sharedPref.getString(context.getString(R.string.jive_token_entity_key), null);
            if ( !TextUtils.isEmpty(string) ) {
                return json.fromJson(string, TokenEntity.class);
            }
        } catch (IOException e) {
        }
        return null;
    }

    public boolean putTokenEntity(TokenEntity tokenEntity) {
        JiveJson json = new JiveJson();
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.jive_token_entity_key), json.toJson(tokenEntity));
        return editor.commit();
    }

    public boolean clear() {
        JiveJson json = new JiveJson();
        SharedPreferences sharedPref = context.getSharedPreferences(getClass().getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        return editor.commit();
    }

    public boolean doTokensExist() {
        return !TextUtils.isEmpty(getJiveToken()) && !TextUtils.isEmpty(getGithubToken()) && !TextUtils.isEmpty(getEndpoint()) && getTokenEntity() != null;
    }
}
