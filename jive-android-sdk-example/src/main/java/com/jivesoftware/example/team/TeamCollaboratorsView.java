package com.jivesoftware.example.team;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.collaborators.UsersAdapter;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.team.events.TeamCollaboratorLongPressed;
import com.jivesoftware.example.team.events.TeamCollaboratorPressed;

import javax.inject.Inject;

import static android.widget.AdapterView.OnItemClickListener;
import static com.jivesoftware.example.team.TeamCollaboratorsView.Type.TEAM_COLLABORATOR_LONG_PRESSED;
import static com.jivesoftware.example.team.TeamCollaboratorsView.Type.TEAM_COLLABORATOR_PRESSED;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorsView  extends LinearLayout{
    public TypeListenable listenable;
    @InjectView(R.id.team_listview) ListView listView;
    private UsersAdapter adapter;

    public enum Type {
        TEAM_COLLABORATOR_LONG_PRESSED,
        TEAM_COLLABORATOR_PRESSED
    }

    @Inject
    public TeamCollaboratorsView(final Context context, final UsersAdapter adapter, final TypeListenable listenable) {
        super(context);
        this.adapter = adapter;
        this.listenable = listenable;
        inflate(context, R.layout.team_collaborators, this);
        ButterKnife.inject(this);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);
                listenable.post(new TeamCollaboratorLongPressed(user), TEAM_COLLABORATOR_LONG_PRESSED);
                return true;
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);
                listenable.post(new TeamCollaboratorPressed(user), TEAM_COLLABORATOR_PRESSED);
            }
        });
    }

    public void setCollaborators(User[] users) {
        adapter.setUsers(users);
    }
}
