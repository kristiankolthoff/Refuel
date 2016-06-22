package com.roquahacks.model;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.roquahacks.utils.LogoHolder;

import java.util.Objects;

/**
 * Created by Studio on 04.06.2016.
 */
public class Station implements Parcelable, Comparable<Station> {

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
    private static SortPolicyStation sortPolicy;

    private transient Drawable logo;
    private transient int logoID;
    private transient int backgroundID;
    private transient int rank;

    private static String MISSING_NAME_PLACEHOLDER = "-";

    public Station(String name, double lat, double lng, String brand, double dist, double priceE5,
                   double priceE10, double priceDiesel, String id, boolean isOpen, String street,
                   String houseNumber, int postCode, String place) {
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
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.place = place;
    }

    public Station(Parcel parcel) {
        this.name = parcel.readString();
        this.lat = parcel.readDouble();
        this.lng = parcel.readDouble();
        this.brand = parcel.readString();
        this.dist = parcel.readDouble();
        this.priceE5 = parcel.readDouble();
        this.priceE10 = parcel.readDouble();
        this.priceDiesel = parcel.readDouble();
        this.id = parcel.readString();
        this.isOpen = Boolean.valueOf(parcel.readString());
        this.street = parcel.readString();
        this.houseNumber = parcel.readString();
        this.postCode = parcel.readInt();
        this.place = parcel.readString();
        this.backgroundID = parcel.readInt();
        this.logoID = parcel.readInt();
        this.rank = parcel.readInt();
    }

    public static SortPolicyStation getSortPolicy() {
        return sortPolicy;
    }

    public static void setSortPolicy(SortPolicyStation sortPolicy) {
        Station.sortPolicy = sortPolicy;
    }

    public String getName() {
        if(name == null) {
            return MISSING_NAME_PLACEHOLDER;
        }
        return name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
        if(brand == null) {
            return name;
        }
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

    public String getPriceE5Beautified() {
        return this.beautifyPrice(priceE5);
    }

    private String beautifyPrice(double price) {
        String priceString = String.valueOf(price).replace(".",",");
        return priceString.substring(0,priceString.length()-1);
    }

    public void setPriceE5(double priceE5) {
        this.priceE5 = priceE5;
    }

    public double getPriceE10() {
        return priceE10;
    }

    public String getPriceE10Beautified() {
        return this.beautifyPrice(priceE10);
    }

    public void setPriceE10(double priceE10) {
        this.priceE10 = priceE10;
    }

    public double getPriceDiesel() {
        return priceDiesel;
    }

    public String getPriceDieselBeautified() {
        return this.beautifyPrice(priceDiesel);
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

    public Drawable getLogo(Context context) {
        if(logo == null) {
            logo = LogoHolder.getLogo(brand, context);
        }
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public int getLogoID() {
        if(logoID == 0) {
            logoID = LogoHolder.getLogoID(brand);
        }
        return logoID;
    }

    public void setLogoID(int logoID) {
        this.logoID = logoID;
    }

    public int getBackgroundID() {
        if(backgroundID == 0) {
            backgroundID = LogoHolder.getStationBackgroundID(brand);
        }
        return backgroundID;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lng);
        parcel.writeString(this.brand);
        parcel.writeDouble(this.dist);
        parcel.writeDouble(this.priceE5);
        parcel.writeDouble(this.priceE10);
        parcel.writeDouble(this.priceDiesel);
        parcel.writeString(this.id);
        parcel.writeString(String.valueOf(this.isOpen));
        parcel.writeString(this.street);
        parcel.writeString(this.houseNumber);
        parcel.writeInt(this.postCode);
        parcel.writeString(this.place);
        parcel.writeInt(this.backgroundID);
        parcel.writeInt(this.logoID);
        parcel.writeInt(this.rank);
    }

    @Override
    public int compareTo(Station station) {
       if(Station.sortPolicy == SortPolicyStation.DISTANCE) {
            return this.compareBySortPolicy(this.getDist(), station.getDist());
       } else if(Station.sortPolicy == SortPolicyStation.PRICE_E5) {
            return this.compareBySortPolicy(this.getPriceE5(), station.getPriceE5());
       } else if(Station.sortPolicy == SortPolicyStation.PRICE_E10) {
            return this.compareBySortPolicy(this.getPriceE10(), station.getPriceE10());
       } else if(Station.sortPolicy == SortPolicyStation.PRICE_DIESEL) {
            return this.compareBySortPolicy(this.getPriceDiesel(), station.getPriceDiesel());
       }
        return 0;
    }

    private int compareBySortPolicy(double thiz, double that) {
        double dist =  that - thiz;
        if(dist > 0) {
            return -1;
        } else if(dist < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
