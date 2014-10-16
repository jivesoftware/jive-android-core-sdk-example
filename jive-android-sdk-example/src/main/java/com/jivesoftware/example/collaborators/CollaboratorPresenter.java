package com.jivesoftware.example.collaborators;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.collaborators.events.CollaboratorDeleteEvent;
import com.jivesoftware.example.github.dao.User;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.utils.ToastMaker;

import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_DELETE_FAILURE;
import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_DELETE_SUCCESS;
import static com.jivesoftware.example.collaborators.CollaboratorModel.Type.COLLABORATOR_REFRESH_SUCCESS;
import static com.jivesoftware.example.collaborators.CollaboratorsView.Type.DELETE_COLLABORATOR_LONG_PRESS;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class CollaboratorPresenter {
    public static void create(final Activity activity, final ToastMaker toastMaker, final CollaboratorModel model, final CollaboratorsView view) {
        model.listenable.setListener(new IValueListener<User[]>() {
            @Override
            public void onPost(User[] users) {
                view.setCollaborators(users);
            }
        }, COLLABORATOR_REFRESH_SUCCESS);

        view.listenable.setListener(new IValueListener<CollaboratorDeleteEvent>() {
            @Override
            public void onPost(CollaboratorDeleteEvent event) {
                model.deleteCollaborator(event.id);
            }
        }, DELETE_COLLABORATOR_LONG_PRESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.collaborator_deleted);
            }
        }, COLLABORATOR_DELETE_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.collaborator_deleted_failure);
            }
        }, COLLABORATOR_DELETE_FAILURE);


        model.refresh();

        activity.getActionBar().setTitle(activity.getString(R.string.collaborators));
        activity.setContentView(view);
    }
}
