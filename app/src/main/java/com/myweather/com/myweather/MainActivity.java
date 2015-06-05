package com.myweather.com.myweather;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by SUNNY on 6/1/2015.
 */


public class MainActivity extends ActionBarActivity {

    double lat;
    double longt;
    ArrayList<Weather> mWeather;
    Fragment fragment;
    String locationName;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(MainActivity.this);
        locationName = "";
        GPSTracker gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation()){
            lat = gps.getLatitude();
            longt = gps.getLongitude();
            LoadLocationWeather weather = new  LoadLocationWeather(lat, longt);
            weather.execute();


        } else{
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void showList() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        WeatherFragment frg =  new WeatherFragment();
        frg.locationName = locationName;
        ft.add(R.id.myContainer, frg, "");
        ft.commit();
    }

    public void setWeather(ArrayList<Weather> mWeather) {
        this.mWeather = mWeather;
    }
    public ArrayList<Weather> getWeather() {
        return this.mWeather;
    }

    private class LoadLocationWeather extends AsyncTask<String, Long, Long> {
        HttpURLConnection connection = null;
        double lat;
        double lon;
        ArrayList<Weather> weather;

        LoadLocationWeather(double lat, double lon){
            this.lat = lat;
            this.lon = lon;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Long doInBackground(String... strings) {
            String dataString = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon="+ lon +"&cnt=10&mode=json";
            Log.d("URL", dataString);
            try {
                URL dataUrl = new URL(dataString);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                if (status == 200) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();

                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String weatherString = sb.toString();

                    locationName = getLocationName(weatherString);
                    weather = Weather.makeList(weatherString);
                    db.addRows(weather);
                    return 0l;
                } else {
                    return 1l;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 1l;
            } catch (IOException e) {
                e.printStackTrace();
                return 1l;
            } catch (Exception e) {
                e.printStackTrace();
                return 1l;
            }
            finally
            {
                if (connection != null)
                    connection.disconnect();
            }

        }
        @Override
        protected void onPostExecute(Long result) {
            if (result != 1l) {
                setWeather(weather);
                showList();

            } else {
                Toast.makeText(getApplicationContext(), "AsyncTask didn't complete", Toast.LENGTH_LONG).show();
            }
        }
    }


    public String getLocationName(String response){

        try{
            JSONObject jsonResponseObject = new JSONObject(response);
            JSONArray daysList = jsonResponseObject.optJSONArray("list");
            JSONObject locaionObject = jsonResponseObject.optJSONObject("city");

            return locaionObject.optString("name");

        } catch (Exception e){

        }
        return  "";

    }
}
