package com.jivesoftware.example.repositories;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;

import javax.inject.Inject;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesView extends LinearLayout {
    public final TypeListenable typeListenable;

    private final RepositoryAdapter adapter;

    @InjectView(R.id.repo_listview)
    ListView listView;

    @Inject
    public RepositoriesView(Context context, RepositoryAdapter repositoryAdapter, TypeListenable typeListenable) {
        super(context);

        View view = inflate(context, R.layout.repositories, this);
        ButterKnife.inject(this, view);

        this.adapter = repositoryAdapter;
        listView.setAdapter(adapter);
        this.typeListenable = typeListenable;
    }

    public void setRepositories(Repository[] repositories) {
        adapter.setRepositories(repositories);
    }
}
