package br.unisinos.ubicomp.centraldoaluno;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class PreferencesAdapter {
    public static final String PREF_USERNAME = "pref_userName";
    public static final String PREF_DEVICEID = "pref_deviceId";
    public static final String PREF_TOKEN = "pref_token";
    private static final String TAG = PreferencesAdapter.class.getSimpleName();
    private static PreferencesAdapter singleton;
    private final SharedPreferences sharedPrefs;
    private Context context;

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

    public void setUserName(@NonNull String username) {
        sharedPrefs.edit().putString(PREF_USERNAME, username).apply();
    }

    public String getDeviceId() {
        return sharedPrefs.getString(PREF_DEVICEID, "");
    }

    public void setDeviceId(@NonNull String deviceid) {
        sharedPrefs.edit().putString(PREF_DEVICEID, deviceid).apply();
    }

    public String getToekn() {
        return sharedPrefs.getString(PREF_TOKEN, "");
    }

    public void setToken(@NonNull String token) {
        sharedPrefs.edit().putString(PREF_TOKEN, token).apply();
    }

    public void clearToken() {
        sharedPrefs.edit().putString(PREF_TOKEN, context.getText(R.string.null_token).toString()).apply();
    }

    public void clear() {
        sharedPrefs.edit().clear().apply();
    }
}
