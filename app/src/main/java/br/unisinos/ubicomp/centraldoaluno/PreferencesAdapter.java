package br.unisinos.ubicomp.centraldoaluno;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PreferencesAdapter {
    private Context context;
    private final SharedPreferences sharedPrefs;

    private static final String PREF_USERNAME = "pref_userName";
    private static final String PREF_DEVICEID = "pref_deviceId";

    private static PreferencesAdapter singleton;

    private PreferencesAdapter(Context context) {
        this.context = context;
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesAdapter getInstance(Context context) {
        if (singleton == null) {
            singleton = new PreferencesAdapter(context);
        }
        return singleton;
    }

    public String getUserName() {
        return sharedPrefs.getString(PREF_USERNAME, "");
    }

    public String getDeviceId() {
        return sharedPrefs.getString(PREF_DEVICEID, "");
    }

    public void setUserName(@NonNull String username) {
        sharedPrefs.edit().putString(PREF_USERNAME, username).apply();
    }

    public void setDeviceId(@NonNull String deviceid) {
        sharedPrefs.edit().putString(PREF_DEVICEID, deviceid).apply();
    }

    public void clear() {
        sharedPrefs.edit().clear().apply();
    }
}
