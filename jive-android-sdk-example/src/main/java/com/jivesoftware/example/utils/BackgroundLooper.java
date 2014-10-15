package com.jivesoftware.example.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class BackgroundLooper extends Thread {
    private Handler handler;

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler();
        Looper.loop();
    }

    public Handler getHandler() {
        return handler;
    }
}
