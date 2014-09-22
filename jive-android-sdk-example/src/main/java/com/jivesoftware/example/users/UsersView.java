package com.jivesoftware.example.users;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class UsersView extends LinearLayout {
    private UserAdapter adapter;
    private ListView listView;

    public UsersView(Context context) {
        super(context);
        initialize(context);
    }

    public UsersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public UsersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.users, this);

        adapter = new UserAdapter(context);
        listView = (ListView) findViewById(R.id.users_listview);
        listView.setAdapter(adapter);
    }

    public void setUsers(User[] users) {
        adapter.setUsers(users);
    }

}
