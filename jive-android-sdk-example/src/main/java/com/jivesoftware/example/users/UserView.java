package com.jivesoftware.example.users;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/22/14.
 */
public class UserView extends LinearLayout {
    @InjectView(R.id.user_avatar)
    ImageView avatar;
    @InjectView(R.id.user_text)
    TextView textView;

    @Inject
    public UserView(Context context) {
        super(context);
        View view = inflate(context, R.layout.user, this);
        ButterKnife.inject(this, view);
    }

    public void refresh(User user) {
        Picasso.with(getContext()).load(user.avatarUrl).into(avatar);
        textView.setText(user.login);
    }
}
