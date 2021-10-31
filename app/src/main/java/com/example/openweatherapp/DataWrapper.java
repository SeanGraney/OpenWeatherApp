package com.example.openweatherapp;

import java.io.Serializable;
import java.util.ArrayList;

public class DataWrapper implements Serializable {
    private ArrayList<DailyWeather> wrap;

    public DataWrapper(ArrayList<DailyWeather> data) {
        this.wrap = data;
    }

    public ArrayList<DailyWeather> getDailyWeather() {
        return this.wrap;
    }
}
