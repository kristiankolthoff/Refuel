package com.roquahacks.refuel.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.roquahacks.model.station.PriceHistoryEM;
import com.roquahacks.model.station.Result;
import com.roquahacks.refuel.Application;
import com.roquahacks.refuel.R;
import com.roquahacks.service.background.AlarmReceiver;
import com.roquahacks.service.database.RefuelDBHelper;
import com.roquahacks.service.rest.ApplicationCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private TextView mTextViewLogo;
    private ProgressBar mProgressBar;
    private GoogleApiClient mGoogleApiClient;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Intent alarmIntent;

    private final static boolean USE_LOCATION = true;

    public final static int BACKGROUND = Color.parseColor("#FF8E00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTextViewLogo = (TextView) findViewById(R.id.textView_logo);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        Spannable spannable = new SpannableString(getString(R.string.refuel_logo));
        spannable.setSpan(new ForegroundColorSpan(BACKGROUND), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewLogo.setText(spannable, TextView.BufferType.SPANNABLE);

        mProgressBar.getIndeterminateDrawable().setColorFilter(BACKGROUND, PorterDuff.Mode.MULTIPLY);

        RefuelDBHelper dbHelper = RefuelDBHelper.getInstance(this);
        List<PriceHistoryEM> entries = dbHelper.obtainPriceHistoryEM();
        for(PriceHistoryEM entry : entries) {
            Log.d("Refuel", entry.toString());
        }

        alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void startAlarm() {
        if(PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE) == null) {
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), AlarmReceiver.UPDATE_FREQ,
                    PendingIntent.getBroadcast(this, 0, alarmIntent, 0));
            Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
            Log.d("Refuel", "alarm started");
        } else {
            Log.d("Refuel", "alarm already existing");
        }
    }

    private void cancelAlarm() {
        if(alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startApplication();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    SplashActivity.this, 1000);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        Log.d("Refuel", "force close app");
                        finish();
                        break;
                }
            }
        });
    }

    private void startApplication() {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d("Refuel", String.valueOf(mLastLocation.getLatitude()));
        Log.d("Refuel", String.valueOf(mLastLocation.getLongitude()));
        Application.init(this, mLastLocation);
        Application.fetchResults(new ApplicationCallback() {
            @Override
            public void onFinished(Result result) {
                Intent intentMain = new Intent(SplashActivity.this, RefuelActivity.class);
                intentMain.putExtra(Application.INTENT_RES_RESULTS, result);
                Log.d("Refuel", result.toString());
                startAlarm();
                SplashActivity.this.startActivity(intentMain);
            }
        }, USE_LOCATION);

//        //TODO harmful, backend service of geocoder may not be present
//        if(Geocoder.isPresent()) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                List<Address> addresses = geocoder.getFromLocationName("68161", 1);
//                for(Address a : addresses) {
//                    Log.d("Refuel", a.toString());
//                    Log.d("Refuel", String.valueOf(a.getLatitude()));
//                    Log.d("Refuel", String.valueOf(a.getLongitude()));
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //TODO think about attaching the parameters in fetchliststations call
//        Application.updateLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//        Application.fetchListStations(new ServiceCallback() {
//            @Override
//            public void onFinished(ArrayList<Station> stations) {
//                Intent intentMain = new Intent(SplashActivity.this, RefuelActivity.class);
//                intentMain.putParcelableArrayListExtra(Application.STATION_LIST_INTENT_ID, stations);
//                SplashActivity.this.startActivity(intentMain);
//            }
//        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
