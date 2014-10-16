package com.jivesoftware.example.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class ToastMaker {
    @Inject
    public ToastMaker() {}

    public void makeLongToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
