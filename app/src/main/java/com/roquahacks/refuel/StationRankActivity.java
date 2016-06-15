package com.roquahacks.refuel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roquahacks.model.SortPolicyStation;
import com.roquahacks.model.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StationRankActivity extends AppCompatActivity {

    private ArrayList<Station> mStations;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_rank);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mStations = getIntent().getParcelableArrayListExtra(RefuelActivity.STATIONS);
        Station.setSortPolicy(SortPolicyStation.PRICE_DIESEL);
        Collections.sort(mStations);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        StationRankAdapter adapter = new StationRankAdapter(this.mStations, this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
