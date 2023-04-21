package org.antrack.app.modules.apps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modules.Template;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SuppressLint("QueryPermissionsNeeded")
public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Writes installed apps list to the /apps file";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "apps";
    }

    @Override
    public String result() {
        return "/apps";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        String appsFile = AnUtils.getMainDir(context) + result();

        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        try {
            PrintWriter pw = Files.writeLines(appsFile);

            for (ApplicationInfo packageInfo : packages) {
                if (!isSystemPackage(packageInfo))
                    pw.println(pm.getApplicationLabel(packageInfo) + ": " + packageInfo.packageName);
            }

            pw.close();
        } catch (IOException e) {
            return ERROR + e;
        }
        return DONE;
    }

    private boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
