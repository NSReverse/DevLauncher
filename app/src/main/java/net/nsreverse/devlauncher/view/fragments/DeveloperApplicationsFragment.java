package net.nsreverse.devlauncher.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.nsreverse.devlauncher.R;
import net.nsreverse.devlauncher.data.AppResolver;
import net.nsreverse.devlauncher.model.ApplicationEntry;
import net.nsreverse.devlauncher.view.adapter.AppListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Robert on 12/22/2017.
 */

public class DeveloperApplicationsFragment extends Fragment
                                           implements AppListAdapter.Delegate {

    @BindView(R.id.recycler_view_app_list) RecyclerView appListRecyclerView;
    private Context context;
    private Fragment fragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_apps, container, false);

        ButterKnife.bind(this, root);

        fragment = this;

        reloadDataSource();

        return root;
    }

    private void reloadDataSource() {
        AppListAdapter adapter = new AppListAdapter(fragment);
        adapter.setDataSource(AppResolver.getInstalledApps(context, true));

        appListRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        appListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void applicationSelected(int position, ApplicationEntry selectedEntry) {
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(selectedEntry.name.toString());
        startActivity(intent);
    }
}
