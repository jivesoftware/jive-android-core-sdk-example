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
    private EditText oneTimeEditText;
    private View oneTimeLayout;
    private View usernameLayout;
    private View passwordLayout;

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
        oneTimeEditText = (EditText) findViewById(R.id.onetime_edittext);

        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        oneTimeLayout = findViewById(R.id.onetime_layout);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText() != null ? usernameEditText.getText().toString() : "";
                String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
                String onetime = oneTimeEditText.getText() != null ? oneTimeEditText.getText().toString() : "";

                LoginPressed event = new LoginPressed(username, password, onetime);
                listenable.post(event, LOGIN_PRESSED);
            }
        });
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

    public void hideMessage() {
        authenticationMessage.setVisibility(GONE);
    }
}
