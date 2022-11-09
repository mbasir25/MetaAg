package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

public class insert_data extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    Button trace, not_trace;

    BackgroundGeofence mService = null;
    boolean mBound = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BackgroundGeofence.LocalBinder binder = (BackgroundGeofence.LocalBinder)iBinder;
            mService = binder.getService();
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        Dexter.withActivity(this)
                .withPermissions(Arrays.asList(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ))
                .withListener(new MultiplePermissionsListener() {
                                  @Override
                                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                                      trace = findViewById(R.id.trace);
                                      not_trace = findViewById(R.id.not_trace);

                                     trace.setOnClickListener(view -> mService.requestLocationUpdates());
                                     not_trace.setOnClickListener(view -> mService.removeLocationUpdates());
                                     setButtonState(Common.RequestingLocationUpdates(insert_data.this));
                                     bindService(new Intent(insert_data.this,
                                             BackgroundGeofence.class),
                                             mServiceConnection,
                                             Context.BIND_AUTO_CREATE);
                                  }

                                  @Override
                                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                  }
                              }

                ).check();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        if (mBound){
            unbindService(mServiceConnection);
            mBound= false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Common.KEY_REQUESTING_LOCATION_UPDATES))
        {
            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATES, false));

        }
    }

    private void setButtonState(boolean isRequestEnable) {
        if(isRequestEnable){
            trace.setEnabled(false);
            not_trace.setEnabled(true);
        }
        else {
            trace.setEnabled(true);
            not_trace.setEnabled(false);
        }
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event)
    {
        if (event != null)
        {
            String data =new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
        }
    }
    
}