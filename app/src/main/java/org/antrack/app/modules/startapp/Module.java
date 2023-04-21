package org.antrack.app.modules.startapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Start application";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "startapp <package>";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(args[0]);
            context.startActivity(intent);
        } catch (ActivityNotFoundException err) {
            return ERROR + "app not found";
        }
        return DONE;
    }
}
