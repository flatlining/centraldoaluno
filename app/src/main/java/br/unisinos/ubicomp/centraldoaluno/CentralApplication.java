package br.unisinos.ubicomp.centraldoaluno;

import android.app.Application;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.UUID;

public class CentralApplication extends Application {
    private static String TAG = CentralApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        initializeValues();
    }

    private void initializeValues() {
        String deviceName = Build.MODEL;
        String deviceId = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (deviceId == null || deviceId.isEmpty()) {
            deviceId = UUID.randomUUID().toString();
        }

        PreferencesAdapter pa = PreferencesAdapter.getInstance(this);

        if (pa.getUserName() == null || pa.getUserName().isEmpty()) {
            pa.setUserName(deviceName);
        }
        if (pa.getDeviceId() == null || pa.getDeviceId().isEmpty()) {
            pa.setDeviceId(deviceId);
        }

        Log.d(TAG, String.format("User Name: %1s", pa.getUserName()));
        Log.d(TAG, String.format("Device Id: %1s", pa.getDeviceId()));
    }
}
