package com.jivesoftware.example.repositories;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.jivesoftware.example.R;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;

/**
 * Created by mark.schisler on 8/28/14.
 */
public class RepositoriesView extends LinearLayout {
    public TypeListenable typeListenable = new TypeListenable();
    private ListView listView;
    private RepositoryAdapter adapter;

    public RepositoriesView(Context context) {
        super(context);
        initialize(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.repositories, this);

        adapter = new RepositoryAdapter(context);
        listView = (ListView) findViewById(R.id.repo_listview);
        listView.setAdapter(adapter);

    }

    public void setRepositories(Repository[] repositories) {
        adapter.setRepositories(repositories);
    }
}
