package org.antrack.app.modules.hide;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class Pm {
    PackageManager pm;

    public Pm(Context context) {
        pm = context.getPackageManager();
    }

    public void disableMainActivity(String pkg) {
        ComponentName componentName = new ComponentName(pkg, pkg + ".ui.MainActivity");

        pm.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    public void enableMainActivity(String pkg) {
        ComponentName cn = new ComponentName(pkg, pkg + ".ui.MainActivity");

        pm.setComponentEnabledSetting(
                cn,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }
}
