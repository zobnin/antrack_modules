package org.antrack.app.modules.locate;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

import java.io.File;

public class Module extends Template implements ModuleInterface {
    private Location loc;

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Writes coordinates to file /location";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command", "alarm"};
    }

    @Override
    public String command() {
        return "locate";
    }

    @Override
    public String result() {
        return "/location";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public void onLoad(Context context) {
        loc = new Location(context);
    }

    @Override
    public String onCommand(Context context, String[] args) {
        onAlarm(context);
        return DONE;
    }

    @Override
    public void onAlarm(Context context) {
        try {
            String locationsFile = AnUtils.getMainDir(context) + result();
            String time = Utils.currDateAsString("yyyy.MM.dd HH:mm:ss");
            String line = time + " " + loc.getLastLocation();

            Files.backupFileIfNeeded(locationsFile);
            Files.addLine(locationsFile, line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
