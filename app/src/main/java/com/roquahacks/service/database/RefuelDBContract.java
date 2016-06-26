package com.roquahacks.service.database;

import android.provider.BaseColumns;

/**
 * Created by Kolti on 23.06.2016.
 */
public class RefuelDBContract {

    private RefuelDBContract() {}

    public static abstract class ResultEntry implements BaseColumns {
        public static final String TABLE_NAME = "result";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
        public static final String COLUMN_NAME_RADIAN = "radian";
        public static final String COLUMN_NAME_PRICE_E5 = "last_best_price_e5";
        public static final String COLUMN_NAME_PRICE_E10 = "last_best_price_e10";
        public static final String COLUMN_NAME_PRICE_DIESEL = "last_best_price_diesel";
        public static final String COLUMN_NAME_BEST_TIME = "best_refuel_time";
        public static final String COLUMN_NAME_MARKS_CURRENT_LOCATION = "marks_current_location";

    }

    public static abstract  class PriceHistoryEntry implements  BaseColumns {
        public static final String TABLE_NAME = "price_history";
        public static final String COLUMN_NAME_WEEKDAY = "weekday";
        public static final String COLUMN_NAME_AVG_E5 = "avg_e5";
        public static final String COLUMN_NAME_AVG_E10 = "avg_e10";
        public static final String COLUMN_NAME_AVG_DIESEL = "avg_diesel";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_MEASURE_TIME = "measure_time";
    }
 }
