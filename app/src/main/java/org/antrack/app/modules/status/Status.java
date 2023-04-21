package org.antrack.app.modules.status;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import java.util.concurrent.TimeUnit;

class Status {
    private final Context context;
    private final TelephonyManager tManager;
    private final WifiManager wManager;

    Status(Context context) {
        this.context = context;
        tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        wManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    String uptimeString() {
        long seconds = SystemClock.elapsedRealtime() / 1000;
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hour = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        String hourNil = "";
        String minuteNil = "";
        String secondNil = "";

        if (hour < 10) {
            hourNil = "0";
        }
        if (minute < 10) {
            minuteNil = "0";
        }
        if (second < 10) {
            secondNil = "0";
        }

        return day + " days, " + hourNil + hour + ":" + minuteNil + minute + ":" + secondNil + second;
    }

    float batteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return level / (float) scale * 100.0f;
    }

    boolean isBatteryCharging() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }

    String operatorName() {
        return tManager.getNetworkOperatorName();
    }

    boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return wifi.isConnected();
    }

    String wifiName() {
        if (isWifiConnected()) {
            WifiInfo wifiInfo = wManager.getConnectionInfo();
            return wifiInfo.getSSID();
        } else {
            return "not connected";
        }
    }
}
