package org.antrack.app.modules.dial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modules.Template;

import java.util.Locale;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Call to given number";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "dial <num>";
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    public String onCommand(Context context, String[] args) {
        PackageManager pm = context.getPackageManager();
        boolean hasTelephony = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (!hasTelephony) {
            return ERROR + "no phone capabilities";
        }

        String number = args[0].trim();

        if (number.isEmpty() || number.length() > 16) {
            return ERROR + "incorrect number";
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return DONE;
        } else {
            return ERROR + "no phone app";
        }
    }
}
