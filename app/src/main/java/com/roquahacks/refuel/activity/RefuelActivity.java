package com.roquahacks.refuel.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.roquahacks.refuel.R;
import com.roquahacks.model.station.Result;
import com.roquahacks.model.station.Station;
import com.roquahacks.model.rest.RESTConfiguration;
import com.roquahacks.refuel.Application;
import com.roquahacks.service.database.RefuelDBHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class RefuelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
                    GoogleApiClient.OnConnectionFailedListener {

    private RESTConfiguration restConfig;
    private Button buttonCurrFuel;
    Button buttonBestTime;
    Result mResult;
    private GoogleApiClient mGoogleApiClient;
    private Subscription subscription;
    private ArrayList<Station> mMainStations;

    private ImageView imageViewBackground;
    private ImageView imageViewLogo;
    private TextView mTextViewMainLocation;
    private TextView mTextViewPercentage;

    private Button mButtonNavigation;
    private Button mButtonRanking;
    private Button mButtonRefuel;

    private DecoView arcViewPriceE5;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spannable spannable = new SpannableString(getString(R.string.refuel_logo));
        spannable.setSpan(new ForegroundColorSpan(SplashActivity.BACKGROUND), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(spannable);

        mResult = getIntent().getParcelableExtra(Application.INTENT_RES_RESULTS);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                if(mStations != null) {
//                    Intent intent = new Intent(RefuelActivity.this, StationListActivity.class);
//                    intent.putParcelableArrayListExtra(Application.STATION_LIST_INTENT_ID, mStations);
//                    RefuelActivity.this.startActivity(intent);
////                    Intent intent = new Intent(RefuelActivity.this, TestActivity.class);
////                    RefuelActivity.this.startActivity(intent);
//                }
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Depends on sorting criterion
        Station s = mResult.getStations().get(0);
        mMainStations = mResult.getStations();

        imageViewBackground = (ImageView) findViewById(R.id.imageView_main_background);
        imageViewLogo = (ImageView) findViewById(R.id.imageView_main_logo);
        Picasso.with(this)
                .load(s.getBackgroundID())
                .into(imageViewBackground);
        Picasso.with(this)
                .load(s.getLogoID())
                .noFade()
                .into(imageViewLogo);
        mTextViewMainLocation = (TextView) findViewById(R.id.textView_main_location);
        mTextViewPercentage = (TextView) findViewById(R.id.textPercentage);
        arcViewPriceE5 = (DecoView) findViewById(R.id.arcView_priceE5);


        arcViewPriceE5.addSeries(new SeriesItem.Builder(Color.argb(255,218,218,218))
                .setRange(0, 100, 100)
                .setLineWidth(40f)
                .setSpinDuration(5000)
                .setInitialVisibility(false)
                .build());

        SeriesItem series1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0,1.50f,0)
                .setLineWidth(32f)
                .setInitialVisibility(false)
                .build();

        final DecimalFormat df = new DecimalFormat("#.##");
        series1.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float v, float v1) {
                mTextViewPercentage.setText(df.format(v1));
            }

            @Override
            public void onSeriesItemDisplayProgress(float v) {

            }
        });

        int indexSeries1 = arcViewPriceE5.addSeries(series1);

        arcViewPriceE5.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                                .setDelay(1000)
                                .setDuration(2000)
                                .build());

        arcViewPriceE5.addEvent(new DecoEvent.Builder(1.35f).setIndex(indexSeries1).setDelay(4000).build());

        mTextViewMainLocation.setText(String.format(getString(R.string.current_location), s.getPlace(), s.getDist()));

        mButtonNavigation = (Button) findViewById(R.id.button_navigation);
        mButtonNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Station s = mMainStations.get(0);
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + Application.getCurrLat() + "," +
                                Application.getCurrLng() + "&daddr="+s.getLat()+","+s.getLng()));
                startActivity(navigationIntent);
            }
        });
        mButtonRanking = (Button) findViewById(R.id.button_ranking);
        mButtonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMainStations != null) {
                    Intent intent = new Intent(RefuelActivity.this, StationListActivity.class);
                    intent.putParcelableArrayListExtra(Application.INTENT_RES_STATION_LIST, mMainStations);
                    RefuelActivity.this.startActivity(intent);
                }
            }
        });
        mButtonRefuel = (Button) findViewById(R.id.button_refuel);

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Refuel", "onDestroy - persist results");
//        Application.persistResults();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
