package com.roquahacks.refuel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.roquahacks.model.rest.RESTConfiguration;
import com.roquahacks.model.rest.RESTStatus;
import com.roquahacks.model.station.Result;
import com.roquahacks.model.station.Station;
import com.roquahacks.service.database.RefuelDBContract;
import com.roquahacks.service.database.RefuelDBHelper;
import com.roquahacks.service.rest.ApplicationCallback;
import com.roquahacks.service.rest.RESTFuelService;
import com.roquahacks.service.rest.ServiceCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kolti on 22.06.2016.
 */
public class Application {

    private static Context mContext;
    private static RESTFuelService mRestService;
    private static RefuelDBHelper mDbHelper;
    private static Location mLocation;
    private static Result result;

    public static String INTENT_RES_STATION_LIST = "stations";
    public static String INTENT_RES_STATION = "station";
    public static String INTENT_RES_RESULTS = "results";


    public static void init(Context context) {
        mContext = context.getApplicationContext();
        mDbHelper = RefuelDBHelper.getInstance(context);
    }

    public static void init(Context context, Location location) {
        mContext = context.getApplicationContext();
        mDbHelper = RefuelDBHelper.getInstance(context);
        mLocation = location;
    }

    public static void fetchResults(final ApplicationCallback appCallback, boolean useLocation) {
        result = mDbHelper.obtainResult();
        if(result == null) {
            Log.d("Refuel", "No entry found");
            Result r = defaultResult();
            mDbHelper.insertResult(r);
            fetchListStations(r, useLocation, appCallback);
        } else {
            fetchListStations(result, useLocation, appCallback);
        }
    }

    private static Result defaultResult() {
        final int DEFAULT_RADIAN = 5;
        Result result = new Result();
        result.setLat(mLocation.getLatitude());
        result.setLng(mLocation.getLongitude());
        result.setRadian(DEFAULT_RADIAN);
        result.setMarksCurrentLocation(true);
        return result;
    }

    public static void persistResult() {
        result.updateBestPriceE5();
        result.updateBestPriceE10();
        result.updateBestPriceDiesel();
        Log.d("Refuel", "price E10" + String.valueOf(result.getLastBestPriceE5()));
        mDbHelper.updateResult(result);
    }


    private static void fetchListStations(final Result result, boolean useLocation,
                                         final ApplicationCallback appCallback) {
        if(result.marksCurrentLocation() && useLocation) {
            result.getRestConfig().setLat(mLocation.getLatitude());
            result.getRestConfig().setLng(mLocation.getLongitude());
        }
        final RESTConfiguration restConfig = result.getRestConfig();
        Log.d("Refuel", restConfig.toString());
        Subscription subscription = getRestService().fetchListStations(restConfig.getLat(), restConfig.getLng(),
                restConfig.getRadian(), restConfig.getSortPolicy(), restConfig.getFuelType(), RESTConfiguration.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RESTStatus>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Refuel", e.getMessage());
                        Log.d("Refuel", "Error during REST call");
                    }

                    @Override
                    public void onNext(RESTStatus restStatus) {
                        List<Station> stations = restStatus.getStations();
                        Log.d("Refuel", String.valueOf(stations.size()));
                        Log.d("Refuel", restStatus.toString());
                        Log.d("Refuel", restConfig.toString());
                        for (int i = 0; i < stations.size(); i++) {
                            stations.get(i).setRank(i+1);
                            Log.d("Refuel", stations.get(i).toString());
                        }
                        result.setStations(toArrayList(stations));
                        appCallback.onFinished(result);
                    }
                });
    }


    private static RESTFuelService getRestService() {
        if(mRestService == null) {
            try {
                mRestService = new RESTFuelService.Factory()
                        .setCaInput(mContext.getAssets().open("tanker-cert.cer"))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mRestService;
    }

    private static Context getContext() {
        return mContext;
    }

    public static <T> ArrayList<T> toArrayList(List<T> list) {
        ArrayList<T> convertedList = new ArrayList<>();
        for(T t : list) {
            convertedList.add(t);
        }
        return convertedList;
    }

    public static RefuelDBHelper getmDbHelper() {
        return mDbHelper;
    }

    public static Location getCurrLocation() {
        return mLocation;
    }

    public static void setCurrLocation(Location currLocation) {
        Application.mLocation = currLocation;
    }

    public static double getCurrLat() {
        return mLocation.getLatitude();
    }

    public static double getCurrLng() {
        return mLocation.getLongitude();
    }
}
