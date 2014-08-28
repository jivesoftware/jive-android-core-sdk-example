package com.jivesoftware.example.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class ActivityLauncher {
    public void launch(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
