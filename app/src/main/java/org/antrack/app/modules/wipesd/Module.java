package org.antrack.app.modules.wipesd;

import android.content.Context;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Wipe SD card contents";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "wipesd";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        Shell.run("rm -rf /sdcard/*", false);
        return DONE;
    }
}
