package org.antrack.app.modules.locate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

@SuppressLint("MissingPermission")
public class Location {
    private final Context context;

    public Location(Context context) {
        this.context = context;
    }

    public String getLastLocation() {
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        android.location.Location locationGPS = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        android.location.Location locationNet = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;
        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        android.location.Location loc;
        if (0 < GPSLocationTime - NetLocationTime) {
            loc = locationGPS;
        } else {
            loc = locationNet;
        }

        if (loc != null) {
            return loc.getLatitude() + " " + loc.getLongitude();
        } else {
            Log.d("Location", "lastLocation: null location");
            return null;
        }
    }
}
