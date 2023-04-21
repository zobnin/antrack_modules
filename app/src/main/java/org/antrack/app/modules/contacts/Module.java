package org.antrack.app.modules.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modules.Template;

import java.io.PrintWriter;

public class Module extends Template implements ModuleInterface {
    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Dump all contacts in file /contacts";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"command"};
    }

    @Override
    public String command() {
        return "contacts";
    }

    @Override
    public String result() {
        return "/contacts";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public String onCommand(Context context, String[] args) {
        String contactsFile = AnUtils.getMainDir(context) + result();

        Cursor contacts = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (contacts == null) return ERROR + "no contacts";

        try {
            PrintWriter pw = Files.writeLines(contactsFile);

            String name;
            String number;

            while (contacts.moveToNext()) {
                name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                pw.println(name + ": " + number);
            }
            contacts.close();
            pw.close();
        } catch (Exception e) {
            //
        }
        return DONE;
    }
}
