package com.jivesoftware.example.profile;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/20/14.
 */
public class ProfileView extends LinearLayout {
    private final Picasso picasso;
    @InjectView(R.id.github_avatar)
    ImageView avatarView;
    @InjectView(R.id.github_name)
    TextView nameView;
    @InjectView(R.id.github_login)
    TextView loginView;
    @InjectView(R.id.github_location)
    TextView locationView;

    @Inject
    public ProfileView(Context context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
        inflate(context, R.layout.profile, this);
        ButterKnife.inject(this);
    }

    public void refresh(String avatarUrl, String name, String login, String location) {
       picasso.load(avatarUrl).into(avatarView);
       nameView.setText(name);
       loginView.setText("@" + login);
       locationView.setText(location);
    }
}
