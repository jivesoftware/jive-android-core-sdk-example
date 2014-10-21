package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class ActivityLauncher {
    @Inject
    public ActivityLauncher() {}

    public void launch(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public void launchClearTop(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
