package com.jivesoftware.example.teams;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Team;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class TeamView extends LinearLayout {
    @InjectView(R.id.team_title)
    TextView title;

    @Inject
    public TeamView(Context context) {
        super(context);
        View view = inflate(context, R.layout.team, this);
        ButterKnife.inject(this, view);
    }

    public void refresh(Team team) {
        title.setText(team.name);
    }
}
