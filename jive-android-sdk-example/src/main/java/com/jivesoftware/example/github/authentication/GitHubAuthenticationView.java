package com.jivesoftware.example.github.authentication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.authentication.events.GitHubLoginPressed;
import com.jivesoftware.example.listenable.TypeListenable;

import javax.inject.Inject;

import static com.jivesoftware.example.github.authentication.GitHubAuthenticationView.Type.GIT_HUB_LOGIN_PRESSED;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class GitHubAuthenticationView extends LinearLayout {
    @InjectView(R.id.authentication_message) TextView authenticationMessage;
    @InjectView(R.id.username_edittext) EditText usernameEditText;
    @InjectView(R.id.password_edittext) EditText passwordEditText;
    @InjectView(R.id.onetime_edittext) EditText oneTimeEditText;
    @InjectView(R.id.onetime_layout) View oneTimeLayout;
    @InjectView(R.id.username_layout) View usernameLayout;
    @InjectView(R.id.password_layout) View passwordLayout;

    public enum Type {
        GIT_HUB_LOGIN_PRESSED
    }

    public final TypeListenable listenable;

    @Inject
    public GitHubAuthenticationView(Context context, TypeListenable typeListenable) {
        super(context);
        this.listenable = typeListenable;

        inflate(context, R.layout.github_authentication, this);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.login_button)
    public void submitLogin() {
        String username = usernameEditText.getText() != null ? usernameEditText.getText().toString() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
        String onetime = oneTimeEditText.getText() != null ? oneTimeEditText.getText().toString() : "";

        GitHubLoginPressed event = new GitHubLoginPressed(username, password, onetime);
        listenable.post(event, GIT_HUB_LOGIN_PRESSED);
    }

    public void showTwoFactorRequired() {
        usernameLayout.setVisibility(GONE);
        passwordLayout.setVisibility(GONE);
        oneTimeLayout.setVisibility(VISIBLE);
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.two_factor_required));
    }

    public void showAuthenticationRequired() {
        usernameLayout.setVisibility(VISIBLE);
        passwordLayout.setLeft(VISIBLE);
        oneTimeLayout.setVisibility(GONE);
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.bad_username_password));
    }

    public void showOauthFailure() {
        usernameLayout.setVisibility(VISIBLE);
        passwordLayout.setLeft(VISIBLE);
        oneTimeLayout.setVisibility(GONE);
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.oauth_failure));
    }

    public void hideMessage() {
        authenticationMessage.setVisibility(GONE);
    }
}
