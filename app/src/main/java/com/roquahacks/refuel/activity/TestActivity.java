package com.roquahacks.refuel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.roquahacks.model.SortPolicyStation;
import com.roquahacks.model.Station;
import com.roquahacks.refuel.R;
import com.roquahacks.refuel.view.StationRankAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivity extends AppCompatActivity {

    private ArrayList<Station> mStations;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_rank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        StationRankAdapter adapter = new StationRankAdapter(this.mStations, this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
