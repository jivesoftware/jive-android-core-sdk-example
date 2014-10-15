package com.jivesoftware.example.jive.authentication;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.jivesoftware.example.R;
import com.jivesoftware.example.jive.authentication.events.JiveLoginPressed;
import com.jivesoftware.example.listenable.TypeListenable;

import javax.inject.Inject;

import static com.jivesoftware.example.jive.authentication.JiveAuthenticationView.Type.JIVE_LOGIN_PRESSED;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class JiveAuthenticationView extends LinearLayout {
    @InjectView(R.id.authentication_message) TextView authenticationMessage;
    @InjectView(R.id.endpoint_edittext) EditText endpointEditText;
    @InjectView(R.id.username_edittext) EditText usernameEditText;
    @InjectView(R.id.password_edittext) EditText passwordEditText;
    @InjectView(R.id.username_layout) View usernameLayout;
    @InjectView(R.id.password_layout) View passwordLayout;
    public final TypeListenable listenable;

    public enum Type {
        JIVE_LOGIN_PRESSED
    }

    @Inject
    public JiveAuthenticationView(Context context, TypeListenable listenable) {
        super(context);
        this.listenable = listenable;
        inflate(context, R.layout.github_authentication, this);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.login_button)
    public void submitLogin() {
        String endpoint = endpointEditText.getText() != null ? endpointEditText.getText().toString() : "";
        String username = usernameEditText.getText() != null ? usernameEditText.getText().toString() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

        JiveLoginPressed event = new JiveLoginPressed(endpoint, username, password);
        listenable.post(event, JIVE_LOGIN_PRESSED);
    }

    public void showEndpointFailure() {
        usernameLayout.setVisibility(VISIBLE);
        passwordLayout.setLeft(VISIBLE);
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.endpoint_failure));
    }
}
