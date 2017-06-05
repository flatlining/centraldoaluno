package br.unisinos.ubicomp.centraldoaluno;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import java.util.UUID;

public class CentralApplication extends Application {
    private static String TAG = CentralApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        initializeValues();
    }

    private void initializeValues() {
        String deviceName = Build.MODEL;
        String deviceId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (deviceId == null && deviceId.isEmpty()) {
            deviceId = UUID.randomUUID().toString();
        }

        Log.d(TAG, String.format("Device Name: %1s", deviceName));
        Log.d(TAG, String.format("Device Id: %1s", deviceId));
    }
}
