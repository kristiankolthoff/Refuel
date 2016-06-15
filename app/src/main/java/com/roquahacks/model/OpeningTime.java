package com.roquahacks.model;

/**
 * Created by Studio on 14.06.2016.
 */
public class OpeningTime {

    private String text;
    //TODO maybe include real time types
    private String start;
    private String end;

    public OpeningTime(String text, String start, String end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
