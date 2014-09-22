package com.jivesoftware.example.teams;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamView extends LinearLayout {
    private TextView title;

    public TeamView(Context context) {
        super(context);
        initialize(context);
    }

    public TeamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TeamView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.team, this);
        title = (TextView) findViewById(R.id.team_title);
    }

    public void refresh(Team team) {
        title.setText(team.name);
    }
}
