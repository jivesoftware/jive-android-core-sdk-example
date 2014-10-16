package com.jivesoftware.example.profiles;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.listenable.TypeListenable;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 10/15/14.
 */
public class ProfilesView extends LinearLayout {
    private Picasso picasso;
    public TypeListenable listenable;
    @InjectView(R.id.github_avatar) ImageView githubAvatarView;
    @InjectView(R.id.github_name) TextView githubName;
    @InjectView(R.id.jive_avatar) ImageView jiveAvatarView;
    @InjectView(R.id.jive_name) TextView jiveName;

    @Inject
    public ProfilesView(Context context, Picasso picasso, TypeListenable listenable) {
        super(context);
        this.picasso = picasso;
        this.listenable = listenable;
        inflate(context, R.layout.profiles, this);
        ButterKnife.inject(this);
    }

    public void refreshJiveProfile(String name, String avatarUrl) {
        jiveName.setText(name);
        picasso.load(Uri.parse(avatarUrl)).into(jiveAvatarView);
    }

    public void refreshGitHubProfile(String name, String avatarUrl) {
        githubName.setText(name);
        picasso.load(Uri.parse(avatarUrl)).into(githubAvatarView);
    }
}
