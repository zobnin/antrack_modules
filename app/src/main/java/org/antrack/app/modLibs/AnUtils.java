package org.antrack.app.modLibs;

import android.annotation.SuppressLint;
import android.content.Context;

public class AnUtils {
    public static String getMainDir(Context context) {
        String deviceName = getDeviceName();
        String deviceId = getDeviceId(context);
        String deviceNameWithId = getDeviceNameWithId(deviceName, deviceId);

        return context.getApplicationInfo().dataDir + "/" + deviceNameWithId + "/";
    }

    private static String getDeviceName() {
        return android.os.Build.MODEL.toLowerCase();
    }

    private static String getDeviceNameWithId(String deviceName, String deviceId) {
        String deviceNameWithoutSpaces = deviceName.replace(' ', '_');
        String deviceIdLastDigits = deviceId.substring(deviceId.length() - 4);

        return deviceNameWithoutSpaces + "_" + deviceIdLastDigits;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID
        );
    }
}
