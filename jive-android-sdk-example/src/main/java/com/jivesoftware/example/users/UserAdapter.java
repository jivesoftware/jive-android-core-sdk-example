package com.jivesoftware.example.users;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jivesoftware.example.github.dao.User;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by mark.schisler on 9/22/14.
 */
@Singleton
public class UserAdapter extends BaseAdapter {
    private final Provider<UserView> userViewProvider;
    private User[] users = new User[0];

    @Inject
    public UserAdapter(Provider<UserView> userViewProvider) {
        this.userViewProvider = userViewProvider;
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
            userView = userViewProvider.get();
        }
        userView.refresh((User)getItem(position));
        return userView;
    }

    public void setUsers(User[] users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
