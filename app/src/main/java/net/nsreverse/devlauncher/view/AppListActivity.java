package net.nsreverse.devlauncher.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.nsreverse.devlauncher.R;
import net.nsreverse.devlauncher.data.AppResolver;
import net.nsreverse.devlauncher.model.ApplicationEntry;
import net.nsreverse.devlauncher.view.adapter.AppListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppListActivity extends AppCompatActivity
                             implements AppListAdapter.Delegate {

    @BindView(R.id.recycler_view_basic_apps) RecyclerView appListBasicRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        Context context = this;

        ButterKnife.bind(this);


    }

    @Override
    public void applicationSelected(@SuppressWarnings("unused") int position,
                                    ApplicationEntry selectedEntry) {


    }
}
