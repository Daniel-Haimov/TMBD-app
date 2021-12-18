package com.hw.tmbd_test;

import android.app.Application;

import com.hw.tmbd_test.utils.GPS;
import com.hw.tmbd_test.utils.MyVibrate;
import com.hw.tmbd_test.utils.SP;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        SP.initHelper(this);
        GPS.initHelper(this);
        MyVibrate.initHelper(this);

        grantLocationPermission();
    }

    private void grantLocationPermission() {
        /**
         * used only in Activity
         */
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}  , 44);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
    }


}