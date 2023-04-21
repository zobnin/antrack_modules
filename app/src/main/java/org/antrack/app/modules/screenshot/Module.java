package org.antrack.app.modules.screenshot;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Take screenshot";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "screenshot";
    }

    @Override
    public String result() {
        return "/screenshots/";
    }

    @Override
    public String resultType() {
        return "png";
    }

    @Override
    public boolean usesRoot() {
        return true;
    }

    @Override
    public String onCommand(Context context, String[] args) {
        String screenshotsDir = AnUtils.getMainDir(context) + result();

        String path = screenshotsDir + "/" + Utils.currDateAsString("yyyy-MM-dd-HH-mm-ss") + ".png";

        // Touch file for correct permissions
        Shell.run("touch " + path);
        Shell.run("screencap " + path, true);

        return DONE;
    }
}
