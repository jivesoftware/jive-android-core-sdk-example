package com.jivesoftware.example.repositories;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.jivesoftware.example.R;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesView extends LinearLayout {
    public RepositoriesView(Context context) {
        super(context);
        initialize(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.repositories, this);
    }
}
