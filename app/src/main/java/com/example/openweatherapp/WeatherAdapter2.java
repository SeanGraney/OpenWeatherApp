package com.example.openweatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter2 extends RecyclerView.Adapter<MyViewHolder2>{

    private static final String TAG = "WeatherAdapter";
    private List<DailyWeather> weatherList;
    private dayActivity dAct;
    private boolean fahrenheit;

    WeatherAdapter2(List<DailyWeather> weatherList, dayActivity da){
        this.weatherList = weatherList;
        dAct = da;
    }

    //    create visual layout object
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflatedLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather, parent, false);

//        makes the recycler list tap and long tapable
//        inflatedLayout.setOnClickListener(mainAct);
//        inflatedLayout.setOnLongClickListener(mainAct);

        return new MyViewHolder2(inflatedLayout);
    }

    //    sets text in view holder in index position
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        fahrenheit = dAct.getFahrenheit();
        String unit = "°F";
        if (!fahrenheit){
            unit = "°C";
        }


        DailyWeather w = weatherList.get(position);
        String day = w.getDay(w.getDt());
        String tempRange = String.valueOf(Math.round(w.getTempMax()))+unit+"/"+ String.valueOf(Math.round(w.getTempMin()))+unit;
        String morningTemp = String.valueOf(Math.round(w.getTempMorn()))+unit;
        String dayTemp = String.valueOf(Math.round(w.getTempDay()))+unit;
        String eveningTemp = String.valueOf(Math.round(w.getTempEve()))+unit;
        String nightTemp = String.valueOf(Math.round(w.getTempNight()))+unit;
        String description = w.getDescription();
        String precip = "("+String.valueOf(Math.round(w.getPop()))+"% precip.)";
        String uvIndex = "UV Index: "+String.valueOf(Math.round(w.getUvi()));
        int img = dAct.getImg(w.getIcon());

        holder.day.setText(day);
        holder.tempRange.setText(tempRange);
        holder.morningTemp.setText(morningTemp);
        holder.dayTemp.setText(dayTemp);
        holder.eveningTemp.setText(eveningTemp);
        holder.nightTemp.setText(nightTemp);
        holder.description.setText(description);
        holder.precip.setText(precip);
        holder.uvIndex.setText(uvIndex);
        holder.weatherIcon.setImageResource(img);
    }

    //    Returns size of list
    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
