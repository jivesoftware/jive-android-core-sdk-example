package com.jivesoftware.example.users;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.User;
import com.squareup.picasso.Picasso;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class UserView extends LinearLayout {
    private ImageView avatar;
    private TextView textView;

    public UserView(Context context) {
        super(context);
        initialize(context);
    }

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public UserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.user, this);
        avatar = (ImageView) findViewById(R.id.user_avatar);
        textView = (TextView) findViewById(R.id.user_text);
    }

    public void refresh(User user) {
        Picasso.with(getContext()).load(user.avatarUrl).into(avatar);
        textView.setText(user.login);
    }
}
