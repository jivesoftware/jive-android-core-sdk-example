package com.jivesoftware.example.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.jivesoftware.example.destroyer.IDestroyable;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class BackgroundRunner implements IDestroyable {
    private final String TAG = getClass().getName();
    private BackgroundThread backgroundThread;
    private final Handler backgroundHandler;
    private final Handler foregroundHandler;

    public interface JiveBackgroundRunnable<R> {
        R run() throws Exception;
    }

    public interface JiveResultCallback<T> {
        void success(T result);
        void failure();
    }

    public BackgroundRunner(BackgroundThread backgroundThread) {
        this.backgroundThread = backgroundThread;
        backgroundHandler = backgroundThread.getHandler();
        foregroundHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void destroy() {
        backgroundThread.destroy();
    }

    public <R> void post(final JiveBackgroundRunnable<R> runnable, final JiveResultCallback<R> callback) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final R returnValue = runnable.run();
                    foregroundHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.success(returnValue);
                            } catch (Exception e) {
                                Log.e(TAG, "Exception encountered ", e);
                                callback.failure();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Exception encountered ", e);
                    foregroundHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failure();
                        }
                    });
                }
            }
        });
    }
}
