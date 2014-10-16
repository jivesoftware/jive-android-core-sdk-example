package com.jivesoftware.example.collaborators;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.collaborators.events.CollaboratorDeleteEvent;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class CollaboratorsView extends LinearLayout {
    public TypeListenable listenable;
    private final UserAdapter adapter;

    @InjectView(R.id.users_listview)
    ListView listView;

    public enum Type {
        DELETE_COLLABORATOR_LONG_PRESS
    }

    @Inject
    public CollaboratorsView(Context context, final TypeListenable listenable, UserAdapter userAdapter) {
        super(context);
        this.listenable = listenable;
        View view = inflate(context, R.layout.users, this);
        ButterKnife.inject(this, view);

        adapter = userAdapter;
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listenable.post(new CollaboratorDeleteEvent(id), Type.DELETE_COLLABORATOR_LONG_PRESS);
                return true;
            }
        });
    }

    public void setCollaborators(User[] users) {
        adapter.setUsers(users);
    }
}
