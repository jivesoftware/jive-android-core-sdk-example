package com.jivesoftware.example.users;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jivesoftware.example.github.dao.User;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class UserAdapter extends BaseAdapter {
    private User[] users = new User[0];
    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public Object getItem(int position) {
        return users[position];
    }

    @Override
    public long getItemId(int position) {
        return users[position].id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserView userView = (UserView) convertView;
        if ( userView == null ) {
            userView = new UserView(context);
        }
        userView.refresh((User)getItem(position));
        return userView;
    }

    public void setUsers(User[] users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
