package com.roquahacks.model.station;

import java.sql.Timestamp;

/**
 * Created by Kolti on 24.06.2016.
 */
public class PriceHistoryEM {

    private int weekday;
    private String time;
    private double avgE5;
    private double avgE10;
    private double avgDiesel;
    private String timestamp;

    public PriceHistoryEM(int weekday, String time, double avgE5,
                          double avgE10, double avgDiesel, String timestamp) {
        this.weekday = weekday;
        this.time = time;
        this.avgE5 = avgE5;
        this.avgE10 = avgE10;
        this.avgDiesel = avgDiesel;
        this.timestamp = timestamp;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAvgE5() {
        return avgE5;
    }

    public void setAvgE5(double avgE5) {
        this.avgE5 = avgE5;
    }

    public double getAvgE10() {
        return avgE10;
    }

    public void setAvgE10(double avgE10) {
        this.avgE10 = avgE10;
    }

    public double getAvgDiesel() {
        return avgDiesel;
    }

    public void setAvgDiesel(double avgDiesel) {
        this.avgDiesel = avgDiesel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PriceHistoryEM{" +
                "weekday=" + weekday +
                ", time='" + time + '\'' +
                ", avgE5=" + avgE5 +
                ", avgE10=" + avgE10 +
                ", avgDiesel=" + avgDiesel +
                ", timestamp=" + timestamp +
                '}';
    }

}
