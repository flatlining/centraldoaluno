package br.unisinos.ubicomp.centraldoaluno;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String EXTRA_REQUEST_TOKEN = "br.unisinos.ubicomp.centraldoaluno.REQUEST_TOKEN";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    PreferencesAdapter pa;
    private EditText nameEditText;
    private TextView tokenTextView;
    private Button cancelTokenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.coarse_location_request_title);
                builder.setMessage(R.string.coarse_location_request_body);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        if (getIntent().getBooleanExtra(EXTRA_REQUEST_TOKEN, false)) {
            requestToken();
        }

        pa = PreferencesAdapter.getInstance(this);
        Log.d(TAG, String.format("User Name: %1s", pa.getUserName()));
        Log.d(TAG, String.format("Device Id: %1s", pa.getDeviceId()));

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        tokenTextView = (TextView) findViewById(R.id.tokenTextView);
        cancelTokenButton = (Button) findViewById(R.id.cancelTokenButton);

        nameEditText.setText(pa.getUserName());
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pa.setUserName(s.toString());
                Log.d(TAG, String.format("User Name: %1s", pa.getUserName()));
            }
        });

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tokenTextView.setText(PreferencesAdapter.getInstance(this).getToekn());
    }

    private void requestToken() {
        Log.d(TAG, "Request Token!");
        Integer minimum = 1;
        Integer maximum = 10;
        Integer randomNum = minimum + (int) (Math.random() * maximum);

        PreferencesAdapter.getInstance(this).setToken(randomNum.toString());
        Log.d(TAG, String.format("Token %1s", PreferencesAdapter.getInstance(this).getToekn()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.limited_functionality_title);
                    builder.setMessage(R.string.limited_functionality_body);
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PreferencesAdapter.PREF_TOKEN)) {
            Log.d(TAG, String.format("Key: %1s", PreferencesAdapter.getInstance(this).getToekn()));
            tokenTextView.setText(PreferencesAdapter.getInstance(this).getToekn());
        }
    }
}
