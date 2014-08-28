package com.jivesoftware.example.repositories;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RepositoriesView(this));
    }
}
