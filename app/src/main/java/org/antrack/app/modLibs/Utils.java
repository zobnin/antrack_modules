package org.antrack.app.modLibs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String currDateAsString(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(new Date());
    }

    public static String arrayToString(String[] ar) {
        StringBuilder sb = new StringBuilder();
        for (String s : ar) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
