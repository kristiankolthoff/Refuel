package com.roquahacks.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.roquahacks.model.rest.RESTConfiguration;
import com.roquahacks.model.station.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.roquahacks.service.database.RefuelDBContract.ResultEntry;
import static com.roquahacks.service.database.RefuelDBContract.PriceHistoryEntry;

/**
 * Created by Kolti on 23.06.2016.
 */
public class RefuelDBHelper extends SQLiteOpenHelper{

    private static RefuelDBHelper dbHelper;

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_REAL = " REAL";
    private static final String TYPE_INT = " INTEGER";
    private static final String TYPE_TIMESTAMP = " TIMESTAMP";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TABLE_RESULT =
            "CREATE TABLE " + ResultEntry.TABLE_NAME +
                    "(" +
            ResultEntry.COLUMN_NAME_LAT + TYPE_REAL + COMMA_SEP +
            ResultEntry.COLUMN_NAME_LNG + TYPE_REAL + COMMA_SEP +
            ResultEntry.COLUMN_NAME_RADIAN + TYPE_INT + COMMA_SEP +
            ResultEntry.COLUMN_NAME_PRICE_E5 + TYPE_REAL + COMMA_SEP +
            ResultEntry.COLUMN_NAME_PRICE_E10 + TYPE_REAL + COMMA_SEP +
            ResultEntry.COLUMN_NAME_PRICE_DIESEL + TYPE_REAL + COMMA_SEP +
            ResultEntry.COLUMN_NAME_BEST_TIME + TYPE_TEXT + COMMA_SEP +
            ResultEntry.COLUMN_NAME_MARKS_CURRENT_LOCATION + TYPE_INT +
                    ")";

    private static final String CREATE_TABLE_PRICE_HISTORY =
            "CREATE TABLE " + PriceHistoryEntry.TABLE_NAME +
                    "(" +
            PriceHistoryEntry.COLUMN_NAME_WEEKDAY + TYPE_INT + COMMA_SEP +
            PriceHistoryEntry.COLUMN_NAME_MEASURE_TIME + TYPE_TEXT + COMMA_SEP +
            PriceHistoryEntry.COLUMN_NAME_AVG_E5 + TYPE_REAL + COMMA_SEP +
            PriceHistoryEntry.COLUMN_NAME_AVG_E10 + TYPE_REAL + COMMA_SEP +
            PriceHistoryEntry.COLUMN_NAME_AVG_DIESEL + TYPE_REAL + COMMA_SEP +
            PriceHistoryEntry.COLUMN_NAME_TIMESTAMP + TYPE_TEXT +
                    ")";

    private static final String DELETE_TABLE_RESULT =
            "DROP TABLE IF EXISTS " + ResultEntry.TABLE_NAME;

    private static final String DELETE_TABLE_PRICE_HISTORY =
            "DROP TABLE IF EXISTS " + PriceHistoryEntry.TABLE_NAME;


    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Refuel.db";

    private RefuelDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static RefuelDBHelper getInstance(Context context) {
        if(dbHelper == null) {
            dbHelper = new RefuelDBHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE_RESULT);
        onCreate(sqLiteDatabase);
    }

    public void clearTableResult() {
        this.getWritableDatabase().execSQL(DELETE_TABLE_RESULT);
        this.getWritableDatabase().execSQL(CREATE_TABLE_RESULT);
    }

    public void clearTablePriceHistory() {
        this.getWritableDatabase().execSQL(DELETE_TABLE_PRICE_HISTORY);
        this.getWritableDatabase().execSQL(CREATE_TABLE_PRICE_HISTORY);
    }


    public long insertResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        RESTConfiguration rc = result.getRestConfig();
        vals.put(ResultEntry.COLUMN_NAME_LAT, rc.getLat());
        vals.put(ResultEntry.COLUMN_NAME_LNG, rc.getLng());
        vals.put(ResultEntry.COLUMN_NAME_RADIAN, rc.getRadian());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_E5, result.getLastBestPriceE5());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_E10, result.getLastBestPriceE10());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_DIESEL, result.getLastBestPriceDiesel());
        vals.put(ResultEntry.COLUMN_NAME_BEST_TIME, result.getBestRefuelTime());
        vals.put(ResultEntry.COLUMN_NAME_MARKS_CURRENT_LOCATION, result.marksCurrentLocationInt());
        return db.insertOrThrow(ResultEntry.TABLE_NAME, null, vals);
    }


    public void updateResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        RESTConfiguration rc = result.getRestConfig();
        vals.put(ResultEntry.COLUMN_NAME_LAT, rc.getLat());
        vals.put(ResultEntry.COLUMN_NAME_LNG, rc.getLng());
        vals.put(ResultEntry.COLUMN_NAME_RADIAN, rc.getRadian());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_E5, result.getLastBestPriceE5());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_E10, result.getLastBestPriceE10());
        vals.put(ResultEntry.COLUMN_NAME_PRICE_DIESEL, result.getLastBestPriceDiesel());
        vals.put(ResultEntry.COLUMN_NAME_BEST_TIME, result.getBestRefuelTime());
        vals.put(ResultEntry.COLUMN_NAME_MARKS_CURRENT_LOCATION, result.marksCurrentLocationInt());
        int res = db.update(ResultEntry.TABLE_NAME, vals, null, null);
    }

    public Result obtainResult() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(false, ResultEntry.TABLE_NAME, null, null, null, null, null, null, null);
        Result res = null;
        while(c.moveToNext()) {
            res = new Result();
            res.setLat(c.getFloat(c.getColumnIndex(ResultEntry.COLUMN_NAME_LAT)));
            res.setLng(c.getFloat(c.getColumnIndex(ResultEntry.COLUMN_NAME_LNG)));
            res.setRadian(c.getInt(c.getColumnIndex(ResultEntry.COLUMN_NAME_RADIAN)));
            res.setLastBestPriceE5(c.getDouble(c.getColumnIndex(ResultEntry.COLUMN_NAME_PRICE_E5)));
            res.setLastBestPriceE10(c.getDouble(c.getColumnIndex(ResultEntry.COLUMN_NAME_PRICE_E10)));
            res.setLastBestPriceDiesel(c.getDouble(c.getColumnIndex(ResultEntry.COLUMN_NAME_PRICE_DIESEL)));
            res.setBestRefuelTime(c.getString(c.getColumnIndex(ResultEntry.COLUMN_NAME_BEST_TIME)));
            res.setMarksCurrentLocationInt(c.getInt(c.getColumnIndex(ResultEntry.COLUMN_NAME_MARKS_CURRENT_LOCATION)));
        }
        c.close();
        return res;
    }

    public long insertPriceHistoryEvent(PriceHistoryEM phEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(PriceHistoryEntry.COLUMN_NAME_WEEKDAY, phEntry.getWeekday());
        vals.put(PriceHistoryEntry.COLUMN_NAME_MEASURE_TIME, phEntry.getTime());
        vals.put(PriceHistoryEntry.COLUMN_NAME_AVG_E5, phEntry.getAvgE5());
        vals.put(PriceHistoryEntry.COLUMN_NAME_AVG_E10, phEntry.getAvgE10());
        vals.put(PriceHistoryEntry.COLUMN_NAME_AVG_DIESEL, phEntry.getAvgDiesel());
        vals.put(PriceHistoryEntry.COLUMN_NAME_TIMESTAMP, phEntry.getTimestamp().toString());
        return db.insert(PriceHistoryEntry.TABLE_NAME, null, vals);
    }

    public PriceHistoryEM obtainPriceHistoryEM() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(false, PriceHistoryEntry.TABLE_NAME, null, null, null, null, null, null, null);
        Log.d("Refuel", "Num of priceentries:" + c.getCount());
        c.moveToNext();
        int weekday = c.getInt(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_WEEKDAY));
        String time = c.getString(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_MEASURE_TIME));
        double avgE5 = c.getDouble(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_AVG_E5));
        double avgE10 = c.getDouble(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_AVG_E10));
        double avgDiesel = c.getDouble(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_AVG_DIESEL));
        String timestamp = c.getString(c.getColumnIndex(PriceHistoryEntry.COLUMN_NAME_TIMESTAMP));
        c.close();
        return new PriceHistoryEM(weekday, time, avgE5, avgE10, avgDiesel, timestamp);
    }
}
