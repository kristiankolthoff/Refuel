package com.roquahacks.model;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Studio on 04.06.2016.
 */
public class Station {

    private String name;
    private double lat;
    private double lng;
    private String brand;
    private double dist;
    @SerializedName("e5")
    private double priceE5;
    @SerializedName("e10")
    private double priceE10;
    @SerializedName("diesel")
    private double priceDiesel;
    private String id;
    private boolean isOpen;
    private String street;
    private String houseNumber;
    private int postCode;
    private String place;

    public Station(String name, double lat, double lng, String brand, double dist,
                   double priceE5, double priceE10, double priceDiesel, String id,
                   boolean isOpen) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.brand = brand;
        this.dist = dist;
        this.priceE5 = priceE5;
        this.priceE10 = priceE10;
        this.priceDiesel = priceDiesel;
        this.id = id;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getPriceE5() {
        return priceE5;
    }

    public void setPriceE5(double priceE5) {
        this.priceE5 = priceE5;
    }

    public double getPriceE10() {
        return priceE10;
    }

    public void setPriceE10(double priceE10) {
        this.priceE10 = priceE10;
    }

    public double getPriceDiesel() {
        return priceDiesel;
    }

    public void setPriceDiesel(double priceDiesel) {
        this.priceDiesel = priceDiesel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Double.compare(station.lat, lat) == 0 &&
                Double.compare(station.lng, lng) == 0 &&
                Double.compare(station.dist, dist) == 0 &&
                Double.compare(station.priceE5, priceE5) == 0 &&
                Double.compare(station.priceE10, priceE10) == 0 &&
                Double.compare(station.priceDiesel, priceDiesel) == 0 &&
                isOpen == station.isOpen &&
                postCode == station.postCode &&
                Objects.equals(name, station.name) &&
                Objects.equals(brand, station.brand) &&
                Objects.equals(id, station.id) &&
                Objects.equals(street, station.street) &&
                Objects.equals(houseNumber, station.houseNumber) &&
                Objects.equals(place, station.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lat, lng, brand, dist, priceE5, priceE10, priceDiesel, id, isOpen, street, houseNumber, postCode, place);
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", brand='" + brand + '\'' +
                ", dist=" + dist +
                ", priceE5=" + priceE5 +
                ", priceE10=" + priceE10 +
                ", priceDiesel=" + priceDiesel +
                ", id='" + id + '\'' +
                ", isOpen=" + isOpen +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postcode=" + postCode +
                ", place='" + place + '\'' +
                '}';
    }
}
