package com.example.openweatherapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class dayActivity extends AppCompatActivity {

    private static final String TAG = "DailyActivity";
    private ArrayList<DailyWeather> daily = new ArrayList<>();
    private WeatherAdapter2 mAdapter;
    private RecyclerView recyclerView2;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private boolean fahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

//        Change action bar

        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra("location"));  // provide compatibility to all the versions

//      update daily array from main activity
        DataWrapper data = (DataWrapper) getIntent().getSerializableExtra("dailyWeather");
        daily = data.getDailyWeather();
        Log.d("DailyWeatherTransfer: ", daily.toString());

//        initialize recycler
        recyclerView2 = findViewById(R.id.recycler2);

        //        Data to recycler
        mAdapter = new WeatherAdapter2(daily, this);
        recyclerView2.setAdapter(mAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        fahrenheit = getIntent().getBooleanExtra("unit", true);
    }

    public int getImg(String img){
        int iconResId = this.getResources().getIdentifier(img,
                "drawable", this.getPackageName());

        return iconResId;
    }

    public boolean getFahrenheit(){
        return fahrenheit;
    }
}