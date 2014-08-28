package com.jivesoftware.example.authentication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.authentication.events.LoginPressed;
import com.jivesoftware.example.listenable.TypeListenable;

import static com.jivesoftware.example.authentication.AuthenticationView.Type.LOGIN_PRESSED;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class AuthenticationView extends LinearLayout {
    private TextView authenticationMessage;
    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    public enum Type {
        LOGIN_PRESSED
    }

    public TypeListenable listenable = new TypeListenable();

    public AuthenticationView(Context context) {
        super(context);
        initialize(context);
    }

    public AuthenticationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public AuthenticationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.authentication, this);

        authenticationMessage = (TextView) findViewById(R.id.authentication_message);
        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPressed event = new LoginPressed(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                listenable.post(event, LOGIN_PRESSED);
            }
        });
    }

    public void showTwoFactorRequired() {
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.two_factor_required));
    }

    public void showAuthenticationRequired() {
        authenticationMessage.setVisibility(VISIBLE);
        authenticationMessage.setText(getResources().getString(R.string.bad_username_password));
    }

    public void hideMessage() {
        authenticationMessage.setVisibility(GONE);
    }
}
