package org.antrack.app.modules.hide;

import android.content.Context;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Hide app icon";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "hide [on|off]";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        Pm pm = new Pm(context);

        switch (args[0]) {
            case "on":
                pm.disableMainActivity(PKG_NAME);
                break;
            case "off":
                pm.enableMainActivity(PKG_NAME);
                break;
            default:
                return ERROR + "you must use 'hide' command with 'on' or 'off' argument";
        }
        return DONE;
    }
}
