package net.nsreverse.devlauncher.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.nsreverse.devlauncher.DevLauncherApplication;
import net.nsreverse.devlauncher.R;
import net.nsreverse.devlauncher.data.AppResolver;
import net.nsreverse.devlauncher.model.ApplicationEntry;
import net.nsreverse.devlauncher.view.adapter.AppListAdapter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * UserApplicationsFragment -
 *
 * This fragment displays a list of installed apps, not from Android Studio.
 *
 * @author Robert
 * Created on 12/22/2017.
 */
public class UserApplicationsFragment extends Fragment
                                      implements AppListAdapter.Delegate {

    private static final int KEY_UNINSTALL_APP = 2001;

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
        adapter.setDataSource(AppResolver.getInstalledApps(context, false));

        appListRecyclerView.setLayoutManager(new GridLayoutManager(context,
                DevLauncherApplication.RunningConfig.columnCount));
        appListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void applicationSelected(int position, ApplicationEntry selectedEntry) {
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(selectedEntry.name.toString());
        startActivity(intent);
    }

    @Override
    public void applicationLongSelected(int position, ApplicationEntry selectedEntry) {
        // https://stackoverflow.com/questions/7868460/implicit-intent-to-uninstall-application
        Uri packageUri = Uri.parse("package:" + selectedEntry.name);
        Intent uninstallIntent =
                new Intent(Intent.ACTION_DELETE, packageUri);
        startActivityForResult(uninstallIntent, KEY_UNINSTALL_APP);

        if (DevLauncherApplication.RunningConfig.loggingEnabled) {
            String message = String.format(Locale.US, "Application Long Select: %s (%s)",
                    selectedEntry.label, selectedEntry.name);

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KEY_UNINSTALL_APP) {
            reloadDataSource();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
