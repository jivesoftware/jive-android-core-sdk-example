package com.jivesoftware.example.team;

import android.app.Activity;
import com.jivesoftware.example.R;
import com.jivesoftware.example.listenable.IListener;
import com.jivesoftware.example.listenable.IValueListener;
import com.jivesoftware.example.team.events.TeamCollaboratorLongPressed;
import com.jivesoftware.example.team.events.TeamCollaboratorPressed;
import com.jivesoftware.example.team.events.TeamCollaborators;
import com.jivesoftware.example.utils.ToastMaker;

import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_COLLABORATORS_FAILURE;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_COLLABORATORS_SUCCESS;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_USER_DELETE_FAILURE;
import static com.jivesoftware.example.team.TeamCollaboratorsModel.Type.TEAM_USER_DELETE_SUCCESS;
import static com.jivesoftware.example.team.TeamCollaboratorsView.Type.TEAM_COLLABORATOR_LONG_PRESSED;
import static com.jivesoftware.example.team.TeamCollaboratorsView.Type.TEAM_COLLABORATOR_PRESSED;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class TeamCollaboratorsPresenter {
    public static void create(final Activity activity, final TeamCollaboratorsModel model, final TeamCollaboratorsView view, final ToastMaker toastMaker) {
        model.listenable.setListener(new IValueListener<TeamCollaborators>() {
            @Override
            public void onPost(TeamCollaborators event) {
                view.setCollaborators(event.users);
            }
        }, TEAM_COLLABORATORS_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.team_collaborators_failure);
            }
        }, TEAM_COLLABORATORS_FAILURE);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
               toastMaker.makeLongToast(activity, R.string.delete_user_team_success);
            }
        }, TEAM_USER_DELETE_SUCCESS);

        model.listenable.setListener(new IListener() {
            @Override
            public void onPost() {
                toastMaker.makeLongToast(activity, R.string.error_delete_user_team);
            }
        }, TEAM_USER_DELETE_FAILURE);

        view.listenable.setListener(new IValueListener<TeamCollaboratorPressed>() {
            @Override
            public void onPost(TeamCollaboratorPressed event) {

            }
        }, TEAM_COLLABORATOR_PRESSED);

        view.listenable.setListener(new IValueListener<TeamCollaboratorLongPressed>() {
            @Override
            public void onPost(TeamCollaboratorLongPressed event) {
                model.deleteTeamMember(event.user);
            }
        }, TEAM_COLLABORATOR_LONG_PRESSED);

        activity.getActionBar().setTitle(R.string.team_members);
        activity.setContentView(view);
    }

    public static void resume(TeamCollaboratorsModel model) {
        model.refresh();
    }
}
