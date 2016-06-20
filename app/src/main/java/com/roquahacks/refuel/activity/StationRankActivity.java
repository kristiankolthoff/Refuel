package com.roquahacks.refuel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.roquahacks.model.SortPolicyStation;
import com.roquahacks.model.Station;
import com.roquahacks.refuel.R;
import com.roquahacks.refuel.view.StationRankAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class StationRankActivity extends AppCompatActivity {

    private ArrayList<Station> mStations;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_rank);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        mStations = getIntent().getParcelableArrayListExtra(RefuelActivity.STATIONS);
        Station.setSortPolicy(SortPolicyStation.PRICE_DIESEL);
        Collections.sort(mStations);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StationRankAdapter adapter = new StationRankAdapter(this.mStations, this);
        this.recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(StationRankActivity.this, RefuelActivity.class);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
