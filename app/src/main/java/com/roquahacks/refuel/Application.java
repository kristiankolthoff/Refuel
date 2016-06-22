package com.roquahacks.refuel;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.roquahacks.model.RESTConfiguration;
import com.roquahacks.model.RESTStatus;
import com.roquahacks.model.Station;
import com.roquahacks.service.RESTFuelService;
import com.roquahacks.service.ServiceCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
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
    private static RESTConfiguration mRestConfig;
    private static ArrayList<Station> mStations;

    public static String STATION_LIST_INTENT_ID = "stations";
    public static String STATION_INTENT_ID = "station";

    public static void fetchListStations(final ServiceCallback callback) {
        Subscription subscription = getRestService().fetchListStations(mRestConfig.getLat(), mRestConfig.getLng(),
                mRestConfig.getRadian(), mRestConfig.getSortPolicy(), mRestConfig.getFuelType(), RESTConfiguration.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RESTStatus>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Refuel", "Error during REST call");
                    }

                    @Override
                    public void onNext(RESTStatus restStatus) {
                        List<Station> stations = restStatus.getStations();
                        Log.d("Refuel", String.valueOf(stations.size()));
                        Log.d("Refuel", restStatus.toString());
                        Log.d("Refuel", mRestConfig.toString());
                        for (int i = 0; i < stations.size(); i++) {
                            stations.get(i).setRank(i+1);
                            Log.d("Refuel", stations.get(i).toString());
                        }
                        mStations = toArrayList(stations);
                        callback.onFinished(mStations);
                    }
                });
    }

    public static void initDefaultRESTConfig() {
        final int RADIAN = 5;
        final RESTConfiguration.FuelType FUEL_TYPE = RESTConfiguration.FuelType.ALL;
        final RESTConfiguration.SortPolicy SORT_POLICY = RESTConfiguration.SortPolicy.DISTANCE;
        mRestConfig = new RESTConfiguration()
                .setLat(48.5)
                .setLng(8.62)
                .setRadian(RADIAN)
                .setFuelType(FUEL_TYPE)
                .setSortPolicy(SORT_POLICY);
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

    private static RESTConfiguration getRestConfig() {
        if(mRestConfig == null) {
            initDefaultRESTConfig();
        }
        return mRestConfig;
    }

    private static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        Application.mContext = mContext;
    }

    public void setRestConfig(RESTConfiguration restConfig) {
        mRestConfig = restConfig;
    }

    public static void updateLocation(double lat, double lng) {
        mRestConfig.setLat(lat);
        mRestConfig.setLng(lng);
    }

    public static void updateRadius(int radius) {
        mRestConfig.setRadian(radius);
    }

    public static <T> ArrayList<T> toArrayList(List<T> list) {
        ArrayList<T> convertedList = new ArrayList<>();
        for(T t : list) {
            convertedList.add(t);
        }
        return convertedList;
    }


}
