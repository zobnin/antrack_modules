package org.antrack.app.modules.cmd;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

import java.io.FileWriter;
import java.io.IOException;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Run shell command";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "cmd <txt>";
    }

    @Override
    public String result() {
        return "/cmdout";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    public String onCommand(Context context, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
            sb.append(" ");
        }

        String argsStr = sb.toString().trim();
        if (argsStr.isEmpty()) {
            return ERROR + "usage: cmd <shell command>";
        }

        String cmdOutFile = AnUtils.getMainDir(context) + result();

        try {
            FileWriter writer = new FileWriter(cmdOutFile);
            String out = Shell.run(argsStr, false, true);

            boolean noOut = false;

            if (out == null || out.equals("")) {
                out = "no output";
                noOut = true;
            }

            // Workaround: Dropbox don't notify about changes if new file contents identical to old
            writer.write(Utils.currDateAsString("yyyy.MM.dd HH:mm:ss:SSS") + "\n" + out);
            writer.close();

            if (noOut) {
                return ERROR + "no output";
            }

            return DONE;
        } catch (IOException e) {
            return ERROR + e.getMessage();
        }
    }
}
