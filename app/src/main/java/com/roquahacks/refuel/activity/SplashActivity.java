package com.roquahacks.refuel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.roquahacks.model.Station;
import com.roquahacks.refuel.Application;
import com.roquahacks.refuel.R;
import com.roquahacks.service.RESTFuelService;
import com.roquahacks.service.ServiceCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private TextView mTextViewLogo;
    private ProgressBar mProgressBar;
    private GoogleApiClient mGoogleApiClient;

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

        Application.setContext(this);
        Application.initDefaultRESTConfig();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
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
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.d("Refuel", String.valueOf(mLastLocation.getLatitude()));
        Log.d("Refuel", String.valueOf(mLastLocation.getLongitude()));
        //TODO harmful, backend service of geocoder may not be present
        if(Geocoder.isPresent()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocationName("68161", 1);
                for(Address a : addresses) {
                    Log.d("Refuel", a.toString());
                    Log.d("Refuel", String.valueOf(a.getLatitude()));
                    Log.d("Refuel", String.valueOf(a.getLongitude()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO think about attaching the parameters in fetchliststations call
        Application.updateLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        Application.fetchListStations(new ServiceCallback() {
            @Override
            public void onFinished(ArrayList<Station> stations) {
                Intent intentMain = new Intent(SplashActivity.this, RefuelActivity.class);
                intentMain.putParcelableArrayListExtra(Application.STATION_LIST_INTENT_ID, stations);
                SplashActivity.this.startActivity(intentMain);
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
