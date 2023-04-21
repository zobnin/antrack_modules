package org.antrack.app.modules.info;

import android.content.Context;
import android.os.Build;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modules.Template;

import java.io.IOException;
import java.io.PrintWriter;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Writes basic information about device in file /info";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command", "load"};
    }

    @Override
    public String command() {
        return "info";
    }

    @Override
    public String result() {
        return "/info";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        onLoad(context);
        return DONE;
    }

    @Override
    public void onLoad(Context context) {
        String infoFile = AnUtils.getMainDir(context) + result();
        String deviceName = capitalize(Build.BRAND) + " " + capitalize(Build.MODEL);
        String androidVersion = Build.VERSION.RELEASE;
        String path = Build.VERSION.SECURITY_PATCH;
        String uname = Shell.run("uname -r", false, true);

        try {
            PrintWriter pw = Files.writeLines(infoFile);
            pw.println("Device name: " + deviceName);
            pw.println("Android version: " + androidVersion);
            pw.println("Security patch: " + path);
            pw.println("Kernel version: " + uname);
            pw.close();
        } catch (IOException e) {
            //
        }
    }

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
