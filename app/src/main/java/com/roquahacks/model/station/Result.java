package com.roquahacks.model.station;

import android.os.Parcel;
import android.os.Parcelable;

import com.roquahacks.model.rest.RESTConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Kolti on 23.06.2016.
 */
public class Result implements Parcelable{

    private RESTConfiguration restConfig;
    private ArrayList<Station> stations;
    private double lastBestPriceE5;
    private double lastBestPriceE10;
    private double lastBestPriceDiesel;
    private String bestRefuelTime;
    private boolean marksCurrentLocation;


    public Result() {
        restConfig = new RESTConfiguration();
    }

    public Result(RESTConfiguration restConfig, double lastBestPriceE5,
                  double lastBestPriceE10, double lastBestPriceDiesel, String bestRefuelTime,
                  boolean marksCurrentLocation) {
        this.restConfig = restConfig;
        this.stations = stations;
        this.lastBestPriceE5 = lastBestPriceE5;
        this.lastBestPriceE10 = lastBestPriceE10;
        this.lastBestPriceDiesel = lastBestPriceDiesel;
        this.bestRefuelTime = bestRefuelTime;
        this.marksCurrentLocation = marksCurrentLocation;
    }

    protected Result(Parcel in) {
        restConfig = in.readParcelable(RESTConfiguration.class.getClassLoader());
        stations = in.createTypedArrayList(Station.CREATOR);
        lastBestPriceE5 = in.readDouble();
        lastBestPriceE10 = in.readDouble();
        lastBestPriceDiesel = in.readDouble();
        bestRefuelTime = in.readString();
        marksCurrentLocation = Boolean.valueOf(String.valueOf(in.readInt()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(restConfig, flags);
        dest.writeTypedList(stations);
        dest.writeDouble(lastBestPriceE5);
        dest.writeDouble(lastBestPriceE10);
        dest.writeDouble(lastBestPriceDiesel);
        dest.writeString(bestRefuelTime);
        dest.writeInt(marksCurrentLocation ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public int marksCurrentLocationInt() {
        return marksCurrentLocation ? 1 : 0;
    }

    public boolean marksCurrentLocation() {
        return marksCurrentLocation;
    }

    public void setMarksCurrentLocationInt(int marksCurrentLocationInt) {
        marksCurrentLocation = marksCurrentLocationInt == 1 ? true : false;
    }

    public void setMarksCurrentLocation(boolean marksCurrentLocation) {
        this.marksCurrentLocation = marksCurrentLocation;
    }

    public RESTConfiguration getRestConfig() {
        return restConfig;
    }


    public void setRestConfig(RESTConfiguration restConfig) {
        this.restConfig = restConfig;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public double getLastBestPriceE5() {
        return lastBestPriceE5;
    }

    public void setLastBestPriceE5(double lastBestPriceE5) {
        this.lastBestPriceE5 = lastBestPriceE5;
    }

    public double getLastBestPriceE10() {
        return lastBestPriceE10;
    }

    public void setLastBestPriceE10(double lastBestPriceE10) {
        this.lastBestPriceE10 = lastBestPriceE10;
    }

    public double getLastBestPriceDiesel() {
        return lastBestPriceDiesel;
    }

    public void setLastBestPriceDiesel(double lastBestPriceDiesel) {
        this.lastBestPriceDiesel = lastBestPriceDiesel;
    }


    public String getBestRefuelTime() {
        return bestRefuelTime;
    }

    public void setBestRefuelTime(String bestRefuelTime) {
        this.bestRefuelTime = bestRefuelTime;
    }

    public void setLat(double lat) {
        this.restConfig.setLat(lat);
    }

    public void setLng(double lng) {
        this.restConfig.setLng(lng);
    }

    public void setRadian(int radian) {
        this.restConfig.setRadian(radian);
    }

    public void updateBestPriceE5() {
        if(stations != null) {
            Station.setSortPolicy(SortPolicyStation.PRICE_E5);
            Collections.sort(stations);
            lastBestPriceE5 = stations.get(0).getPriceE5();
        }
    }

    public void updateBestPriceE10() {
        if(stations != null) {
            Station.setSortPolicy(SortPolicyStation.PRICE_E10);
            Collections.sort(stations);
            lastBestPriceE10 = stations.get(0).getPriceE10();
        }
    }

    public void updateBestPriceDiesel() {
        if(stations != null) {
            Station.setSortPolicy(SortPolicyStation.PRICE_DIESEL);
            Collections.sort(stations);
            lastBestPriceDiesel = stations.get(0).getPriceDiesel();
        }
    }


    @Override
    public String toString() {
        return "Result{" +
                "restConfig=" + restConfig +
                ", stations=" + stations +
                ", lastBestPriceE5=" + lastBestPriceE5 +
                ", lastBestPriceE10=" + lastBestPriceE10 +
                ", lastBestPriceDiesel=" + lastBestPriceDiesel +
                ", bestRefuelTime='" + bestRefuelTime + '\'' +
                ", marksCurrentLocation=" + marksCurrentLocation +
                '}';
    }
}
