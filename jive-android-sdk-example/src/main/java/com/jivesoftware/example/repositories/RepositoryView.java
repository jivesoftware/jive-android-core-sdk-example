package com.jivesoftware.example.repositories;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.Constants;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.utils.IntentUtils;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoryView extends LinearLayout {
    @InjectView(R.id.repository_title)
    TextView title;

    @InjectView(R.id.repository_description)
    TextView description;

    @Inject
    public RepositoryView(Context context) {
        super(context);
        View view = inflate(context, R.layout.repository, this);
        ButterKnife.inject(this, view);
    }

    public void refresh(final Repository repository) {
        title.setText(repository.fullName);
        description.setText(repository.description);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( repository.owner.type.equals(Constants.ORGANIZATION_TYPE)) {
                    IntentUtils.startTeamActivity(getContext(), repository);
                } else {
                    IntentUtils.startCollaboratorActivity(getContext(), repository);
                }
            }
        });
    }
}
