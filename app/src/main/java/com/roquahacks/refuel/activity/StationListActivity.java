package com.roquahacks.refuel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.roquahacks.model.Station;
import com.roquahacks.refuel.R;
import com.roquahacks.refuel.view.StationRankAdapter;

import java.util.List;

public class StationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private StationRankAdapter mStationRankAdapter;
    private Menu mMenu;
    private Toolbar mToolbar;
    private boolean mShowAsList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Refuel", "onCreate");
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_station_list_actitvity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setElevation(7);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        List<Station> stations = getIntent().getParcelableArrayListExtra(RefuelActivity.STATIONS);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview_station);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mStaggeredLayoutManager);
        mStationRankAdapter = new StationRankAdapter(stations, this);
        mStationRankAdapter.setOnItemClickListener(new StationRankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Station station) {
                Intent intent = new Intent(StationListActivity.this, StationDetailActivity.class);
                intent.putExtra(RefuelActivity.STATION, station);
                //Get corresponding views for transition
                ImageView imageViewBackground = (ImageView) view.findViewById(R.id.imageView_background);
                LinearLayout placeNameHolder = (LinearLayout) view.findViewById(R.id.placeNameHolder);
                View navigationBar = findViewById(android.R.id.navigationBarBackground);
                View statusBar = findViewById(android.R.id.statusBarBackground);
                //Create transition view and name pairs for intent
                Pair<View, String> imagePair = Pair.create((View) imageViewBackground, "tImage");
                Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
                Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
                Pair<View, String> toolbarPair = Pair.create((View) mToolbar, "tToolbar");
                ActivityOptionsCompat tOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(StationListActivity.this,
                        imagePair, holderPair, toolbarPair);
                ActivityCompat.startActivity(StationListActivity.this, intent, tOptions.toBundle());
            }
        });
        mRecyclerview.setAdapter(mStationRankAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.station_list_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.toggle_gridlist:
                this.toggle();
                break;
            case android.R.id.home:
                Intent intent = new Intent(StationListActivity.this, RefuelActivity.class);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return true;
    }

    private void toggle() {
        MenuItem item = mMenu.findItem(R.id.toggle_gridlist);
        if(mShowAsList) {
            mStaggeredLayoutManager.setSpanCount(2);
            mShowAsList = false;
            item.setIcon(R.drawable.ic_action_list);
        } else {
            mStaggeredLayoutManager.setSpanCount(1);
            mShowAsList = true;
            item.setIcon(R.drawable.ic_action_grid);
        }

    }

}
