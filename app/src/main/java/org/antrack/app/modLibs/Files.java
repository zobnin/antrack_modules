package org.antrack.app.modLibs;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public class Files {

    public static void backupFileIfNeeded(String fileName) {
        File oldFile = new File(fileName);
        File newFile = new File(fileName + ".old");

        if (!oldFile.exists()) return;

        try {
            int ln = Files.countLines(fileName);
            if (ln > 1000) {
                boolean success = oldFile.renameTo(newFile);
                if (!success) throw new IOException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int countLines(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;

            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }

            if (endsWithoutNewLine) {
                ++count;
            }

            return count;
        }
    }

    public static PrintWriter writeLines(String filename) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(filename, false)));
    }

    public static void addLine(String filename, String text) throws IOException {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            out.println(text);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
