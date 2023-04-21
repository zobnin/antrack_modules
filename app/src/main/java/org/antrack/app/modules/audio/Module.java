package org.antrack.app.modules.audio;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    private static final int AUDIO_RECORD_LIMIT = 600;

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Record audio in file";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "audio <num>";
    }

    @Override
    public String result() {
        return "/audio/";
    }

    @Override
    public String resultType() {
        return "3gp";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        String audioDir = AnUtils.getMainDir(context) + result();

        try {
            int time = Integer.parseInt(args[0]);
            if (time < 1 || time > AUDIO_RECORD_LIMIT) {
                return ERROR + "time must be from 1 to " + AUDIO_RECORD_LIMIT + " seconds";
            }

            String date = Utils.currDateAsString("yyyy-MM-dd-HH-mm-ss");
            Media.recordAudio(audioDir + "/" + date + ".3gp", time);
        } catch (Exception e) {
            return ERROR + e.getMessage();
        }

        return DONE;
    }
}
