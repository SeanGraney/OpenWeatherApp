package com.example.openweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private static final String TAG = "HourlyAdapter";
    private List<HourlyWeather> weatherList;
    private MainActivity mainAct;
    private boolean fahrenheit;


    WeatherAdapter(List<HourlyWeather> weatherList, MainActivity ma){
        this.weatherList = weatherList;
        mainAct = ma;
    }

//    create visual layout object
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflatedLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather, parent, false);

//        makes the recycler list tap and long tapable
//        inflatedLayout.setOnClickListener(mainAct);
//        inflatedLayout.setOnLongClickListener(mainAct);

        return new MyViewHolder(inflatedLayout);
    }

    //    sets text in view holder in index position
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        fahrenheit = mainAct.getFahrenheit();
        String unit = "°F";
        if (!fahrenheit){
            unit = "°C";
        }

        HourlyWeather w = weatherList.get(position);
        String day = w.getDay(w.getDt());
        String time = w.getTime(w.getDt());
        String temp = String.valueOf(Math.round(w.getTemp()))+unit;
        String description = String.valueOf(w.getDescription());
        int img = mainAct.getImg(w.getIcon());

        holder.day.setText(day);
        holder.time.setText(time);
        holder.hourlyTemp.setText(temp);
        holder.description.setText(description);
        holder.weatherIcon.setImageResource(img);
    }

    //    Returns size of list
    @Override
    public int getItemCount() {
        return weatherList.size();

    }

}


