package org.antrack.app.modules.camera;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
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
        return "Take photo";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "camera [front|back]";
    }

    @Override
    public String result() {
        return "/camera/";
    }

    @Override
    public String resultType() {
        return "jpg";
    }

    public String onCommand(Context context, String[] args) {
        return takePhoto(context, args[0]);
    }

    @Override
    public void onScreenOn(Context context) {
        File lostFile = new File(AnUtils.getMainDir(context) + "/.lost");

        if (lostFile.exists()) {
            takePhoto(context, "front");
        }
    }

    private String takePhoto(Context context, String which) {
        String cameraDir = AnUtils.getMainDir(context) + result();
        SilentCamera2 camera = new SilentCamera2(context);

        if (!which.trim().equals("front") && !which.trim().equals("back")) {
            return ERROR + "usage: camera [front|back]";
        }

        if (camera.hasCamera()) {
            camera.takeSilentPhoto(which.trim(), cameraDir);
            return DONE;
        } else {
            return ERROR + "camera not found";
        }
    }
}
