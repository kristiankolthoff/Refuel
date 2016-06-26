package com.roquahacks.service.rest;

import com.roquahacks.model.station.Station;

import java.util.ArrayList;

/**
 * Created by Kolti on 22.06.2016.
 */
public interface ServiceCallback {

    public void onFinished(ArrayList<Station> stations);
}
