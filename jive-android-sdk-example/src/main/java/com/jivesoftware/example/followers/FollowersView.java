package com.jivesoftware.example.followers;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.collaborators.UsersAdapter;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersView extends LinearLayout {
    @InjectView(R.id.followers_listview) ListView listView;
    private UsersAdapter adapter;
    public TypeListenable listenable;

    @Inject
    public FollowersView(Context context, UsersAdapter adapter, TypeListenable listenable) {
        super(context);
        inflate(context, R.layout.followers, this);
        ButterKnife.inject(this);

        this.adapter = adapter;
        this.listenable = listenable;
        listView.setAdapter(adapter);
    }

    public void setFollowers(User[] users) {
        adapter.setUsers(users);
    }

    public void showSearchError() {
        // TODO
    }
}
