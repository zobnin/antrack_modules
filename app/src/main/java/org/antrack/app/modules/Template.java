package org.antrack.app.modules;

import android.content.Context;

public class Template implements ModuleInterface {
    public String version() {
        return "1.0";
    }

    public String author() {
        return "Evgeny Zobnin (zobnin@gmail.com)";
    }

    public String desc() {
        return "";
    }

    public String[] startWhen() {
        return null;
    }

    public String command() {
        return "";
    }

    public String result() {
        return "";
    }

    public String resultType() {
        return "";
    }

    public boolean usesRoot() {
        return false;
    }

    public boolean usesAdmin() {
        return false;
    }

    public String onCommand(Context context, String[] args) {
        return "error: module don't have command interface";
    }

    public void onBoot(Context context) {
    }

    public void onLoad(Context context) {
    }

    public void onAlarm(Context context) {
    }

    public void onScreenOn(Context context) {
    }

    public void onIncomingCall(Context context, String phoneNumber) {
    }

    public void onOutgoingCall(Context context, String phoneNumber) {
    }
}
