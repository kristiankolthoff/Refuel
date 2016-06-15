package com.roquahacks.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Studio on 14.06.2016.
 */
public class StationDetail extends Station{

    private boolean wholeDay;
    private String state;
    private List<OpeningTime> openingTimes;

    public StationDetail(String name, double lat, double lng, String brand, double dist, double priceE5, double priceE10,
                         double priceDiesel, String id, boolean isOpen, String street, String houseNumber, int postCode,
                         String place, boolean wholeDay, String state, List<OpeningTime> openingTimes) {
        super(name, lat, lng, brand, dist, priceE5, priceE10, priceDiesel, id, isOpen, street, houseNumber, postCode, place);
        this.wholeDay = wholeDay;
        this.state = state;
        this.openingTimes = openingTimes;
    }

    public static final Parcelable.Creator<Station> CREATOR =
            new Parcelable.Creator<Station>(){
                @Override
                public Station createFromParcel(Parcel source) {
                    return new Station(source);
                }

                @Override
                public Station[] newArray(int size) {
                    return new Station[size];
                }
            };

    public boolean isWholeDay() {
        return wholeDay;
    }

    public void setWholeDay(boolean wholeDay) {
        this.wholeDay = wholeDay;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<OpeningTime> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<OpeningTime> openingTimes) {
        this.openingTimes = openingTimes;
    }
}

