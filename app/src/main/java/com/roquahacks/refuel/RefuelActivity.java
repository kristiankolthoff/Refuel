package com.roquahacks.refuel;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.roquahacks.model.RESTStatus;
import com.roquahacks.model.Station;
import com.roquahacks.model.RESTConfiguration;
import com.roquahacks.service.RESTFuelService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RefuelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private RESTConfiguration restConfig;
    private Button buttonCurrFuel;
    Button buttonBestTime;
    ArrayList<Station> mStations;
    private GoogleApiClient mGoogleApiClient;
    private Subscription subscription;
    public static String STATIONS = "stations";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(mStations != null) {
                    Intent intent = new Intent(RefuelActivity.this, StationRankActivity.class);
                    intent.putParcelableArrayListExtra(RefuelActivity.STATIONS, mStations);
                    RefuelActivity.this.startActivity(intent);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.buttonCurrFuel = (Button) findViewById(R.id.button_curr_fuel);
        this.buttonCurrFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefuelActivity.this, FuelContextActivity.class);
                RefuelActivity.this.startActivity(intent);
            }
        });
        this.buttonBestTime = (Button) findViewById(R.id.button_best_time);



        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        final double LAT = 49.488520;
        final double LNG = 8.462758;
        final int RADIAN = 5;
        final RESTConfiguration.FuelType FUEL_TYPE = RESTConfiguration.FuelType.ALL;
        final RESTConfiguration.SortPolicy SORT_POLICY = RESTConfiguration.SortPolicy.DISTANCE;
        RESTConfiguration restConfig = new RESTConfiguration()
                .setLat(LAT)
                .setLng(LNG)
                .setRadian(RADIAN)
                .setFuelType(FUEL_TYPE)
                .setSortPolicy(SORT_POLICY);
        RESTFuelService fuelService = null;
        try {
            fuelService = new RESTFuelService.Factory()
                    .setCaInput(getAssets().open("tanker-cert.cer"))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subscription = fuelService.fetchListStations(restConfig.getLat(),
                restConfig.getLng(), restConfig.getRadian(), restConfig.getSortPolicy(),
                restConfig.getFuelType(), RESTConfiguration.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RESTStatus>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Refuel", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Refuel", "Error during REST call");
                    }

                    @Override
                    public void onNext(RESTStatus restStatus) {
                        List<Station> stations = restStatus.getStations();
                        for(Station s : stations) {
                            Log.d("Refuel", s.toString());

                        }
                        mStations = toArrayList(stations);
                    }
                });


    }

    public <T> ArrayList<T> toArrayList(List<T> list) {
        ArrayList<T> convertedList = new ArrayList<>();
        for(T t : list) {
            convertedList.add(t);
        }
        return convertedList;
    }


    @Override
    protected void onStart() {
        this.mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        this.mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refuel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("Refuel", String.valueOf(mLastLocation.getLatitude()));
            Log.d("Refuel", String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
