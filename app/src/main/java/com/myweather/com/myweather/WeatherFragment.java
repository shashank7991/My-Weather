package com.myweather.com.myweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.text.format.DateFormat;
import com.myweather.com.myweather.dummy.DummyContent;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WeatherFragment extends Fragment implements AbsListView.OnItemClickListener {

    String[] mTitles;
    ArrayList<Weather> weather;
    EditText location;
    String locationName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        MainActivity activity = (MainActivity)this.getActivity();
        weather = activity.getWeather();
        mTitles = new String[weather.size()];



        for(int i = 0; i< mTitles.length; i++){
            mTitles[i] =  getDate(weather.get(i).timeStamp * 1000);
        }

        ListView lv =(ListView) view.findViewById(R.id.weatherList);

       location = (EditText) view.findViewById(R.id.location);
        location.setText(locationName);
        lv.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, mTitles));
        lv.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        WeatherDetailFragment pf = new WeatherDetailFragment();
        pf.setWeather(weather.get(i));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.myContainer, pf);
        ft.addToBackStack("weather");
        ft.commit();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }
}
