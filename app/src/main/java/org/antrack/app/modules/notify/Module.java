package org.antrack.app.modules.notify;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Create notification";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "notify <text>";
    }

    @SuppressLint("NotificationPermission")
    @Override
    public String onCommand(Context context, String[] args) {
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(APP_NAME)
                .setContentText(Utils.arrayToString(args))
                .setSmallIcon(android.R.drawable.ic_dialog_info);

        if (Build.VERSION.SDK_INT >= 26) {
                builder.setChannelId("main");
        }

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());

        return DONE;
    }
}
