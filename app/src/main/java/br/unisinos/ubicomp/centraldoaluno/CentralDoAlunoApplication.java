package br.unisinos.ubicomp.centraldoaluno;

import android.app.Application;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.UUID;

public class CentralDoAlunoApplication extends Application implements BootstrapNotifier {
    private static final String TAG = CentralDoAlunoApplication.class.getSimpleName();

    private RegionBootstrap regionBootstrap;
    private BeaconManager beaconManager;
    private BackgroundPowerSaver backgroundPowerSaver;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        initializeValues();

        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        Region region = new Region("centralDoAluno", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);

        backgroundPowerSaver = new BackgroundPowerSaver(this);
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
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, String.format("didEnterRegion(%1s)", region.getUniqueId()));
    }

    @Override
    public void didExitRegion(Region region) {
        Log.d(TAG, String.format("didExitRegion(%1s)", region.getUniqueId()));
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        // irrelevant
    }
}
