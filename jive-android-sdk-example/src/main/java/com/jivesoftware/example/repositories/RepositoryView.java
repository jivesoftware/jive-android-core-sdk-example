package com.jivesoftware.example.repositories;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoryView extends LinearLayout {
    private TextView title;
    private TextView description;

    public RepositoryView(Context context) {
        super(context);
        initialize(context);
    }

    public RepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public RepositoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    public void initialize(Context context) {
        inflate(context, R.layout.repository, this);
        title = (TextView) findViewById(R.id.repository_title);
        description = (TextView) findViewById(R.id.repository_description);
    }

    public void refresh(Repository repository) {
        title.setText(repository.fullName);
        description.setText(repository.description);
    }
}
