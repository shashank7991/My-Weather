package com.myweather.com.myweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SUNNY on 6/1/2015.
 */
public class Weather {

    long timeStamp;
    float dayTemp, mornTemp, minTemp, maxTemp, nightTemp, eveTemp;
    float pressure, speed;
    int humidity;
    String weatherType, description, icon;
    int deg, clouds;

    Weather(JSONObject day){
        this.timeStamp = (long) day.optLong("dt");
        JSONObject temp = day.optJSONObject("temp");
        dayTemp = (float) temp.optDouble("day");
        mornTemp = (float) temp.optDouble("morn");
        minTemp = (float) temp.optDouble("min");
        maxTemp = (float) temp.optDouble("max");
        nightTemp = (float) temp.optDouble("night");
        eveTemp = (float) temp.optDouble("eve");
        pressure = (float) day.optDouble("pressure");
        humidity = (int) day.optInt("humidity");
        speed = (float) day.optDouble("speed");
        deg = (int) day.optDouble("deg");
        clouds = (int) day.optDouble("clouds");

        JSONArray whArray = day.optJSONArray("weather");
        JSONObject wh = null;
        try {
            wh = (JSONObject)whArray.get(0);
            weatherType = (String)wh.optString("main");
            description = (String) wh.optString("description");
            icon = (String) wh.optString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<Weather> makeList (String weatherData) throws JSONException {
        ArrayList<Weather> weathers = new ArrayList<>();
        JSONObject data = new JSONObject(weatherData);
        JSONArray days = data.optJSONArray("list");
        for(int i = 0; i < days.length(); i++){
            JSONObject day = (JSONObject)days.get(i);
            Weather currentDay = new Weather(day);
            weathers.add(currentDay);
        }
        return weathers;
    }

}
