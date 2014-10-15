package com.jivesoftware.example.utils;

import android.util.Log;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class BackgroundRunner {
    private final String TAG = getClass().getName();
    private final BackgroundLooper looper;

    public interface JiveBackgroundRunnable<R> {
        R run() throws Exception;
    }

    public interface JiveResultCallback<T> {
        void success(T result);
        void error();
    }

    public BackgroundRunner(BackgroundLooper looper) {
        this.looper = looper;
    }

    public <R> void post(final JiveBackgroundRunnable<R> runnable, final JiveResultCallback<R> callback) {
        looper.getHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.success(runnable.run());
                } catch (Exception e) {
                    Log.e(TAG, "Exception encountered ", e);
                    callback.error();
                }
            }
        });
    }
}
