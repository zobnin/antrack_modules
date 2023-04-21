package org.antrack.app.modules.sms;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Send SMS message";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "sms <num> <txt>";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        PackageManager pm = context.getPackageManager();
        boolean hasTelephony = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (!hasTelephony) {
            return ERROR + "no phone capabilities";
        }

        String number = args[0];
        String text = extractSecondArg(args);

        if (!number.matches("[0-9]{11}")) {
            return ERROR + "incorrect number";
        }
        if (text.isEmpty() || text.length() > 140) {
            return ERROR + "incorrect message";
        }

        SmsManager.getDefault().sendTextMessage(number, null, text, null, null);

        return DONE;
    }

    private String extractSecondArg(String[] args) {
        StringBuilder sb = new StringBuilder();

        for (String s : args) {
            if (s.equals(args[0])) {
                continue;
            }
            sb.append(s);
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
