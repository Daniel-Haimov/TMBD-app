package com.hw.tmbd_test.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.hw.tmbd_test.callbacks.CallBack_GPS;

public class GPS {
    private static final double LOCATION_LAT_DEFAULT = 0.0;
    private static final double LOCATION_LON_DEFAULT = 0.0;
    private static GPS me;
    Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    public static GPS getMe() {
        return me;
    }

    private GPS(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public static void initHelper(Context context) {
        if (me == null) {
            me = new GPS(context);
        }
    }

    private boolean checkPermission(){
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    public void getLocation(CallBack_GPS cb){
        if (checkPermission()) {
            fusedLocationClient.getLastLocation().addOnCompleteListener( task->{
                Location location = task.getResult();
                if(location!=null){
                    cb.getLocation(location.getLatitude(), location.getLongitude());
                }
                else{
                    cb.getLocation(LOCATION_LAT_DEFAULT, LOCATION_LON_DEFAULT);
                }
            });
        }
        else {
            cb.getLocation(LOCATION_LAT_DEFAULT, LOCATION_LON_DEFAULT);
        }
    }

}
