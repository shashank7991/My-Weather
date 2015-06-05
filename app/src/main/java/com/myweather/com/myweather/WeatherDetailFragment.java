package com.myweather.com.myweather;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by SUNNY on 6/1/2015.
 */

public class WeatherDetailFragment extends Fragment {

    Weather weather;
    ImageView imageIcon;
    EditText twWeatherType, day, max,min, night, speed, eve, pressure, humidity, deg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        twWeatherType = (EditText) view.findViewById(R.id.weatherType);
        twWeatherType.setText(weather.weatherType);
        imageIcon = (ImageView)view.findViewById(R.id.imageView);

        day = (EditText) view.findViewById(R.id.day);
        max = (EditText) view.findViewById(R.id.maxText);
        min = (EditText) view.findViewById(R.id.minText);
        night = (EditText) view.findViewById(R.id.night);
        speed = (EditText) view.findViewById(R.id.speed);
        eve = (EditText) view.findViewById(R.id.eve);
        pressure = (EditText) view.findViewById(R.id.prsr);
        humidity = (EditText) view.findViewById(R.id.humid);
        deg = (EditText) view.findViewById(R.id.deg);

        day.setText(weather.dayTemp + "");
        max.setText(weather.maxTemp + "");
        min.setText(weather.minTemp + "");
        eve.setText(weather.eveTemp + "");
        night.setText(weather.nightTemp + "");
        speed.setText(weather.speed + "");
        pressure.setText(weather.pressure + "");
        humidity.setText(weather.humidity + "");
        deg.setText(weather.deg + "");


        Picasso.with(getActivity())
                .load("http://openweathermap.org/img/w/" + weather.icon + ".png")
                .into(imageIcon);

        return view;
    }

    public void setWeather(Weather weather){
        this.weather = weather;
    }
}
