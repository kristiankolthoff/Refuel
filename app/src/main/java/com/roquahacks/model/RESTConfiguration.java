package com.roquahacks.model;

/**
 * Created by Studio on 06.06.2016.
 */
public class RESTConfiguration {

    //TODO needs to be a singleton
    private double lat;
    private double lng;
    private int radian;
    private SortPolicy sortPolicy;
    private FuelType fuelType;

    private static int MAX_RADIAN = 25;
    private static int MIN_RADIAN = 1;
    public static String API_KEY = "xxxx";
    public static String BASE_URL = "https://creativecommons.tankerkoenig.de/";

    public RESTConfiguration(FuelType fuelType, double lat, double lng, int radian, SortPolicy sortPolicy) {
        this.fuelType = fuelType;
        this.lat = lat;
        this.lng = lng;
        this.radian = radian;
        this.sortPolicy = sortPolicy;
    }

    public RESTConfiguration() {

    }

    public RESTConfiguration setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public RESTConfiguration setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public RESTConfiguration setRadian(int radian) {
        if(radian > MAX_RADIAN || radian < MIN_RADIAN) {
            return this;
        } else {
            this.radian = radian;
            return this;
        }
    }

    public RESTConfiguration setSortPolicy(SortPolicy sortPolicy) {
        this.sortPolicy = sortPolicy;
        return this;
    }

    public RESTConfiguration setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getRadian() {
        return radian;
    }

    public SortPolicy getSortPolicy() {
        return sortPolicy;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public static enum SortPolicy {

        PRICE ("price"),
        DISTANCE ("dist");

        private String name;

        SortPolicy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
           return name;
        }
    }

    public static enum FuelType {

        E5 ("e5"),
        E10 ("e10"),
        DIESEL ("diesel"),
        ALL ("all");

        private String name;

        FuelType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }
}

