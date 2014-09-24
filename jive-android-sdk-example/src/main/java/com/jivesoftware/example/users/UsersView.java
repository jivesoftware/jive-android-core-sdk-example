package com.jivesoftware.example.users;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.User;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class UsersView extends LinearLayout {
    private final UserAdapter adapter;

    @InjectView(R.id.users_listview)
    ListView listView;

    @Inject
    public UsersView(Context context, UserAdapter userAdapter) {
        super(context);
        View view = inflate(context, R.layout.users, this);
        ButterKnife.inject(this, view);

        adapter = userAdapter;
        listView.setAdapter(adapter);
    }

    public void setUsers(User[] users) {
        adapter.setUsers(users);
    }
}
