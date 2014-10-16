package com.jivesoftware.example.collaborators;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IValueListener;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class CollaboratorPresenter {
    public static void create(final Activity activity, final CollaboratorModel model, final UsersView view) {
        model.listenable.setListener(new IValueListener<User[]>() {
            @Override
            public void onPost(User[] users) {
                view.setUsers(users);
            }
        }, CollaboratorModel.Type.COLLABORATOR_REFRESH_SUCCESS);

        model.refresh();
        activity.getActionBar().setTitle(activity.getString(R.string.collaborators));
        activity.setContentView(view);
    }
}
