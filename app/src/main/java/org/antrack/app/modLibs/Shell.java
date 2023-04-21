package org.antrack.app.modLibs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    static public void run(String cmd) {
        run(cmd, false, false);
    }

    static public void run(String cmd, boolean needsu) {
        run(cmd, needsu, false);
    }

    static public String run(String cmd, boolean needsu, boolean needout) {
        try {
            String su = "sh";
            if (needsu) {
                su = "su";
            }

            Process process = Runtime.getRuntime().exec(new String[]{su, "-c", cmd});
            if (!needout) return null;

            StringBuffer output = readProcessOutput(process);
            process.waitFor();

            return output.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuffer readProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int read;
        char[] buffer = new char[4096];
        StringBuffer output = new StringBuffer();

        while ((read = reader.read(buffer)) > 0) {
            output.append(buffer, 0, read);
        }

        reader.close();

        return output;
    }
}
