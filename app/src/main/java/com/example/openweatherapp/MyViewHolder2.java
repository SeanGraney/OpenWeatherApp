package com.example.openweatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyViewHolder2 extends RecyclerView.ViewHolder{

    TextView day;
    TextView tempRange;
    TextView nightTemp;
    TextView morningTemp;
    TextView dayTemp;
    TextView eveningTemp;
    ImageView weatherIcon;
    TextView description;
    TextView precip;
    TextView uvIndex;

    public MyViewHolder2(@NonNull View view) {
        super(view);
        day = view.findViewById(R.id.dayDate3);
        tempRange = view.findViewById(R.id.highLow);
        nightTemp = view.findViewById(R.id.nightTemp2);
        morningTemp = view.findViewById(R.id.morningTemp3);
        dayTemp = view.findViewById(R.id.dayTemp2);
        eveningTemp = view.findViewById(R.id.eveningTemp2);
        weatherIcon = view.findViewById(R.id.imageView2);
        description = view.findViewById(R.id.description2);
        precip = view.findViewById(R.id.precipitation2);
        uvIndex = view.findViewById(R.id.uvIndex2);
    }

}
