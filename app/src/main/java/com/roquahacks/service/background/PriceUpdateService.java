package com.roquahacks.service.background;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.roquahacks.model.station.PriceHistoryEM;
import com.roquahacks.model.station.Result;
import com.roquahacks.model.station.Station;
import com.roquahacks.refuel.Application;
import com.roquahacks.service.database.RefuelDBHelper;
import com.roquahacks.service.rest.ApplicationCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Kolti on 24.06.2016.
 */
public class PriceUpdateService extends IntentService{

    private List<Station> mStations;
    private Context context;

    private static final boolean USE_LOCATION = false;

    public PriceUpdateService(String name) {
        super(name);
        mStations = new ArrayList<>();
        context = this;
    }

    public  PriceUpdateService() {
        super("PriceUpdateService");
        mStations = new ArrayList<>();
        context = this;
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Application.init(context);
        Application.fetchResults(new ApplicationCallback() {
            @Override
            public void onFinished(Result result) {
                Log.d("Refuel", "results size: " + result);
                mStations.addAll(result.getStations());
                double avgE5 = 0;
                double avgE10 = 0;
                double avgDiesel = 0;
                for(Station s : mStations) {
                    avgE5 += s.getPriceE5();
                    avgE10 += s.getPriceE10();
                    avgDiesel += s.getPriceDiesel();
                }
                avgE5 /= mStations.size();
                avgE10 /= mStations.size();
                avgDiesel /= mStations.size();
                Calendar calendar = Calendar.getInstance();
                int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                long timestamp = System.currentTimeMillis();
                PriceHistoryEM entry = new PriceHistoryEM(weekday,String.valueOf(hour),avgE5,avgE10,avgDiesel,String.valueOf(timestamp));
                Log.d("Refuel", entry.toString());
                mStations.clear();
                RefuelDBHelper dbHelper = RefuelDBHelper.getInstance(context);
                dbHelper.insertPriceHistoryEvent(entry);
                dbHelper.obtainPriceHistoryEM();
            }
        }, USE_LOCATION);
    }
}
