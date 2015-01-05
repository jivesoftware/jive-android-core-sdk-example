package com.jivesoftware.example.github.authentication;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.bindroid.BindingMode;
import com.bindroid.converters.BoolConverter;
import com.bindroid.converters.ResourceStringConverter;
import com.bindroid.ui.EditTextTextProperty;
import com.bindroid.ui.UiBinder;
import com.jivesoftware.example.R;

import javax.inject.Inject;

public class GitHubAuthenticationView extends LinearLayout {
    private final GitHubAuthenticationModel model;

    @InjectView(R.id.username_edittext) EditText usernameEditText;
    @InjectView(R.id.password_edittext) EditText passwordEditText;
    @InjectView(R.id.onetime_edittext) EditText oneTimeEditText;

    @Inject
    public GitHubAuthenticationView(Context context, GitHubAuthenticationModel model) {
        super(context);
        this.model = model;

        inflate(context, R.layout.github_authentication, this);
        ButterKnife.inject(this);

        UiBinder.bind(this, R.id.username_layout, "Visibility", model, "UsernameAndPasswordAccepted", BindingMode.ONE_WAY, new BoolConverter(true));
        UiBinder.bind(this, R.id.password_layout, "Left", model, "UsernameAndPasswordAccepted", BindingMode.ONE_WAY, new BoolConverter(true));
        UiBinder.bind(this, R.id.onetime_layout, "Visibility", model, "TwoFactorRequired", BindingMode.ONE_WAY, new BoolConverter());
        UiBinder.bind(this, R.id.authentication_message, "Visibility", model, "AuthFailed", BindingMode.ONE_WAY, new BoolConverter());
        UiBinder.bind(this, R.id.authentication_message, "Text", model, "AuthMessageId", BindingMode.ONE_WAY, new ResourceStringConverter(getResources()));

        UiBinder.bind(new EditTextTextProperty(usernameEditText), model, "Username", BindingMode.TWO_WAY);
        UiBinder.bind(new EditTextTextProperty(passwordEditText), model, "Password", BindingMode.TWO_WAY);
        UiBinder.bind(new EditTextTextProperty(oneTimeEditText), model, "Onetime", BindingMode.TWO_WAY);
    }

    @OnClick(R.id.login_button)
    public void submitLogin() {
        model.obtainBasicAuth();
    }
}
