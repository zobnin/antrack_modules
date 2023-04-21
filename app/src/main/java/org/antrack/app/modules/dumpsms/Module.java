package org.antrack.app.modules.dumpsms;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modules.Template;

import java.io.PrintWriter;
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
        return "Dump SMS messages to /sms directory";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "dumpsms";
    }

    @Override
    public String result() {
        return "/sms/";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        PackageManager pm = context.getPackageManager();
        boolean hasTelephony = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (!hasTelephony) {
            return "error: no phone capabilities";
        }

        String inboxFile = AnUtils.getMainDir(context) + result() + "/inbox";
        String sentFile = AnUtils.getMainDir(context) + result() + "/sent";

        dumpSMS(context, sentFile, "sent");
        return dumpSMS(context, inboxFile, "inbox");
    }

    private String dumpSMS(Context context, String file, String box) {
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://sms/" + box),
                null, null, null, null);

        try {
            PrintWriter pw = Files.writeLines(file);

            if (cursor != null && cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    String address = null;
                    String date = null;
                    String body = null;

                    //String person = null;
                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        switch (cursor.getColumnName(idx)) {
                            case "address":
                                address = cursor.getString(idx);
                                break;
                            case "date":
                                date = cursor.getString(idx);
                                break;
                            case "body":
                                body = cursor.getString(idx);
                        }
                    }

                    if (box.equals("inbox")) {
                        pw.println("From: " + address);
                    } else {
                        pw.println("To: " + address);
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US);
                    String dateString = formatter.format(new Date(Long.parseLong(date)));

                    pw.println("Date: " + dateString);

                    if (body != null) {
                        pw.println("Body: " + body.replace('\n', ' '));
                    } else {
                        pw.println("Body: ");
                    }

                    pw.println();
                } while (cursor.moveToNext());
            } else {
                return ERROR + "no SMS";
            }
            pw.close();
            cursor.close();
        } catch (Exception e) {
            //
        }

        return DONE;
    }
}
