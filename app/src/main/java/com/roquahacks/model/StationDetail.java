package com.roquahacks.model;

import java.util.List;

/**
 * Created by Studio on 14.06.2016.
 */
public class StationDetail extends Station{

    private boolean wholeDay;
    private String state;
    private List<OpeningTime> openingTimes;

    public StationDetail(String name, double lat, double lng, String brand, double dist,
                         double priceE5, double priceE10, double priceDiesel, String id, boolean isOpen) {
        super(name, lat, lng, brand, dist, priceE5, priceE10, priceDiesel, id, isOpen);
    }

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

