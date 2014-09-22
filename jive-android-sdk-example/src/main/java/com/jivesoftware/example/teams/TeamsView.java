package com.jivesoftware.example.teams;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamsView extends LinearLayout {
    private TeamsAdapter adapter;
    private ListView listView;

    public TeamsView(Context context) {
        super(context);
        initialize(context);
    }

    public TeamsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TeamsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.teams, this);

        adapter = new TeamsAdapter(context);
        listView = (ListView) findViewById(R.id.team_listview);
        listView.setAdapter(adapter);
    }

    public void setTeams(Team[] repositories) {
        adapter.setTeams(repositories);
    }
}
