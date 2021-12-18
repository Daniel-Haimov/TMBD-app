package com.hw.tmbd_test.utils;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

@SuppressWarnings("unused")
public class MyVibrate {

    public final int DEFAULT_DURATION = 500;

    private static MyVibrate me;
    private final Vibrator vibrator;

    public static MyVibrate getMe() {
        return me;
    }

    private MyVibrate(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void initHelper(Context context) {
        if (me == null) {
            me = new MyVibrate(context);
        }
    }

    public void Vibrate(int vibrateDuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrateDuration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(vibrateDuration);
        }
    }

    public void Vibrate() {
        Vibrate(DEFAULT_DURATION);
    }
}
