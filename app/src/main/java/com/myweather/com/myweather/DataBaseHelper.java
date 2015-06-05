package com.myweather.com.myweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;


/**
 * Created by SUNNY on 6/3/2015.
 */


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final  String weatherTable = "weather";

    private String[] projection = {
            "timeStamp",
            "max",
            "min",
            "day",
            "night",
            "morning",
            "pressure",
            "speed",
            "humidity",
            "weatherType",
            "description",
            "icon",
            "deg",
            "clouds",
            "evening"
    };
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + weatherTable + "(" +
                    "timeStamp TEXT NOT NULL, " +
                    "max FLOAT NOT NULL, " +
                    "min FLOAT NOT NULL, " +
                    "day FLOAT NOT NULL, " +
                    "night FLOAT NOT NULL, " +
                    "morning FLOAT NOT NULL, " +
                    "pressure FLOAT NOT NULL, " +
                    "speed FLOAT NOT NULL, " +
                    "humidity FLOAT NOT NULL, " +
                    "weatherType TEXT NOT NULL, " +
                    "description TEXT NOT NULL, " +
                    "icon TEXT NOT NULL, " +
                    "deg FLOAT NOT NULL, " +
                    "clouds FLOAT NOT NULL, " +
                    "evening FLOAT NOT NULL " +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + weatherTable;

    public DataBaseHelper(Context context) {
        super(context, "weather", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void insertWeather(Weather mWeather) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("timeStamp", mWeather.timeStamp);
        cv.put("max", mWeather.maxTemp);
        cv.put("min", mWeather.minTemp);
        cv.put("day", mWeather.dayTemp);
        cv.put("night", mWeather.nightTemp);
        cv.put("morning", mWeather.mornTemp);
        cv.put("pressure", mWeather.pressure);
        cv.put("speed", mWeather.speed);
        cv.put("humidity", mWeather.humidity);
        cv.put("weatherType", mWeather.weatherType);
        cv.put("description", mWeather.description);
        cv.put("icon", mWeather.icon);
        cv.put("deg", mWeather.deg);
        cv.put("clouds", mWeather.clouds);
        cv.put("evening", mWeather.eveTemp);
        db.insert(weatherTable, null, cv);

    }




    public Cursor getAllRows() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query( weatherTable , projection, null, null, null, null, null);
    }

    public Cursor getRowByTimeStamp(long timeStamp) {
        SQLiteDatabase db = getReadableDatabase();
        String[] timeStamps = {String.valueOf(timeStamp)};
        return db.query(weatherTable, projection, "timeStamp==?", timeStamps, null, null, null);
    }

    public void deleteRowTimeStamp(long timeStamp) {
        SQLiteDatabase db = getWritableDatabase();
        String[] timeStamps = {String.valueOf(timeStamp)};
        db.delete(weatherTable,  "timeStamp==?", timeStamps);
    }

    public void addRows(List<Weather> weathers) {
        for (Weather weather : weathers) {
            insertWeather(weather);
        }
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + weatherTable);
    }

    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }


}
