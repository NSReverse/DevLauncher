package net.nsreverse.devlauncher.data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import net.nsreverse.devlauncher.DevLauncherApplication;
import net.nsreverse.devlauncher.model.ApplicationEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 12/22/2017.
 */

public class AppResolver {
    public static List<ApplicationEntry> getInstalledApps(Context context,
                                                          boolean useFilteredItems) {

        PackageManager manager = context.getPackageManager();
        List<ApplicationEntry> appList = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(intent, 0);

        for (ResolveInfo currentInfo : availableActivities) {
            ApplicationEntry entry = new ApplicationEntry();
            entry.label = currentInfo.loadLabel(manager);
            entry.name = currentInfo.activityInfo.packageName;
            entry.icon = currentInfo.activityInfo.loadIcon(manager);

            String filter = DevLauncherApplication.RunningConfig.FILTER;

            if (useFilteredItems) {
                if (entry.name.toString().trim().contains(filter)) {
                    appList.add(entry);
                }
            }
            else {
                if (!entry.name.toString().trim().contains(filter)) {
                    appList.add(entry);
                }
            }

            if (DevLauncherApplication.RunningConfig.loggingEnabled) {
                System.out.println("=====================================");
                System.out.println("    Label: " + entry.label);
                System.out.println("    Name : " + entry.name);
            }
        }

        return appList;
    }
}
