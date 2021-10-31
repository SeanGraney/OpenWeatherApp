package com.example.openweatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView day;
    TextView time;
    TextView hourlyTemp;
    TextView description;
    ImageView weatherIcon;

    public MyViewHolder(@NonNull View view) {
        super(view);
        day = view.findViewById(R.id.day);
        time = view.findViewById(R.id.time);
        hourlyTemp = view.findViewById(R.id.hourlyTemp);
        weatherIcon = view.findViewById(R.id.imageView3);
        description = view.findViewById(R.id.Description);
    }

}
