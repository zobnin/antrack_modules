package org.antrack.app.modules.status;

import android.content.Context;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modules.Template;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Write phone status information in file /status";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "status";
    }

    @Override
    public String result() {
        return "/status";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        onAlarm(context);
        return DONE;
    }

    @Override
    public void onAlarm(Context context) {
        String statusFile = AnUtils.getMainDir(context) + result();
        Status status = new Status(context);

        try {
            PrintWriter pw = Files.writeLines(statusFile);

            pw.println("Battery: " + getBatteryLevel(status));
            pw.println("Operator: " + getOperator(status));
            pw.println("WiFi: " + status.wifiName());
            pw.println("Uptime: " + status.uptimeString());
            pw.println("Last update: " + getCurrentTimeAsString());

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getBatteryLevel(Status status) {
        String charging = "";

        if (status.isBatteryCharging()) {
            charging = " (charging)";
        }

        return Math.round(status.batteryLevel()) + "%" + charging;
    }

    private String getOperator(Status status) {
        String operator = status.operatorName();

        if (operator == null) {
            operator = "none";
        }

        return operator;
    }

    private String getCurrentTimeAsString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US);
        return dateFormat.format(new Date());
    }
}
