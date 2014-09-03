package com.jivesoftware.example.repositories;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jivesoftware.example.github.dao.Repository;

/**
 * Created by mark.schisler on 9/3/14.
 */
public class RepositoryAdapter extends BaseAdapter {
    private Context context;
    private Repository[] repositories = new Repository[0];

    public RepositoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getCount() {
        return repositories.length;
    }

    @Override
    public Object getItem(int position) {
        return repositories[position];
    }

    @Override
    public long getItemId(int position) {
        return repositories[position].id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepositoryView view = (RepositoryView) convertView;
        if ( view == null ) {
            view = new RepositoryView(context);
        }
        view.refresh((Repository) getItem(position));
        return view;
    }

    public void setRepositories(Repository[] repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }
}
