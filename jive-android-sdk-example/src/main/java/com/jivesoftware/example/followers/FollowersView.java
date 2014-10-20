package com.jivesoftware.example.followers;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.collaborators.UsersAdapter;
import com.jivesoftware.example.followers.events.FollowerSelected;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.utils.ToastMaker;

import javax.inject.Inject;

import static com.jivesoftware.example.followers.FollowersView.Type.FOLLOWER_SELECTED;

/**
 * Created by mark.schisler on 10/16/14.
 */
public class FollowersView extends LinearLayout {
    @InjectView(R.id.followers_listview)
    ListView listView;
    private UsersAdapter adapter;
    public TypeListenable listenable;
    private ToastMaker toastMaker;


    public enum Type {
        FOLLOWER_SELECTED
    }

    @Inject
    public FollowersView(Context context, final UsersAdapter adapter, final TypeListenable listenable, final ToastMaker toastMaker) {
        super(context);
        inflate(context, R.layout.followers, this);
        ButterKnife.inject(this);

        this.adapter = adapter;
        this.listenable = listenable;
        this.toastMaker = toastMaker;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);
                listenable.post(new FollowerSelected(user), FOLLOWER_SELECTED);
            }
        });
    }

    public void setFollowers(User[] users) {
        adapter.setUsers(users);
    }
}

