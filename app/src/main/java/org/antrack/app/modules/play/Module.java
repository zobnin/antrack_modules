package org.antrack.app.modules.play;

import android.content.Context;
import android.media.MediaPlayer;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

import java.io.File;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Play audio file";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "play <path>";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        final MediaPlayer mp = new MediaPlayer();

        if (args[0] == null) {
            return ERROR + "no arguments";
        }

        if (!new File(args[0]).exists()) {
            return ERROR + "file not exist";
        }

        try {
            mp.setDataSource(args[0]);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            //
        }

        return DONE;
    }
}
