package org.antrack.app.modules.logcalls;

import android.app.Service;
import android.content.Context;
import android.telephony.TelephonyManager;

import org.antrack.app.modLibs.AnUtils;
import org.antrack.app.modules.ModuleInterface;
import org.antrack.app.modLibs.Files;
import org.antrack.app.modLibs.Shell;
import org.antrack.app.modLibs.Utils;
import org.antrack.app.modules.Template;

import java.io.File;
import java.io.IOException;

public class Module extends Template implements ModuleInterface {
    private boolean incomingFlag = false;

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public String desc() {
        return "Log all incoming and outgoing calls";
    }

    @Override
    public String[] startWhen() {
        return new String[]{"incomingcall, outgoingcall"};
    }

    @Override
    public String result() {
        return "/calls";
    }

    @Override
    public String resultType() {
        return "txt";
    }

    @Override
    public void onLoad(Context context) {
    }

    @Override
    public void onOutgoingCall(Context context, String phoneNumber) {
        incomingFlag = false;
        put(context, "Outgoing " + phoneNumber);
    }

    @Override
    public void onIncomingCall(Context context, String phoneNumber) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

        switch (tm.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
                incomingFlag = true;
                put(context, "Incoming " + phoneNumber);

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (incomingFlag) {
                    put(context, "Answered " + phoneNumber);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                break;
        }
    }

    private void put(Context context, String txt) {
        try {
            String logsFilePath = AnUtils.getMainDir(context) + result();
            String time = Utils.currDateAsString("yyyy.MM.dd HH:mm:ss");
            String line = time + " " + txt;

            Files.backupFileIfNeeded(logsFilePath);
            Files.addLine(logsFilePath, line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
