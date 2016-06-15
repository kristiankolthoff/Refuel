package com.roquahacks.model;

/**
 * Created by Studio on 14.06.2016.
 */
public class RESTStationDetail {

    private String status;
    private String ok;
    private String license;
    private String data;
    private StationDetail station;

    public RESTStationDetail(String status, String ok, String license,
                             String data, StationDetail station) {
        this.status = status;
        this.ok = ok;
        this.license = license;
        this.data = data;
        this.station = station;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public StationDetail getStation() {
        return station;
    }

    public void setStation(StationDetail station) {
        this.station = station;
    }
}
