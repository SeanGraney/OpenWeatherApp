package com.example.openweatherapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrentWeather {

    
    private double lat;
    private double lon;
    private String timezone;
    private long timezone_offset;
    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feelsLike;
    private long pressure;
    private long humidity;
    private double uvIndex;
    private long clouds;
    private long visibility;
    private double windSpeed;
    private long windDeg;
    private double windGust;
    //    weather -------------------
    private long id;
    private String main;
    private String description;
    private String icon;
    //    ---------------------------
    private String rain;
    private String snow;

    public CurrentWeather(double lat, double lon, String timezone, long timezone_offset, long dt, long sunrise, long sunset, double temp, double feelsLike, long pressure, long humidity, double uvIndex, long clouds, long visibility, double windSpeed, long windDeg, double windGust, long id, String main, String description, String icon, String rain, String snow) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.clouds = clouds;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.windGust = windGust;
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.rain = rain;
        this.snow = snow;
    }



    public long getDt() {
        return dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public long getPressure() {
        return pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public long getClouds() {
        return clouds;
    }

    public long getVisibility() {
        return visibility;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public long getWindDeg() {
        return windDeg;
    }

    public double getWindGust() {
        return windGust;
    }

    public long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return "_"+icon;
    }

    public String getRain() {
        return rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public void setClouds(long clouds) {
        this.clouds = clouds;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDeg(long windDeg) {
        this.windDeg = windDeg;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public String getWind(){
        String dir = getDirection(windDeg);
        long wind = Math.round(windSpeed);

        return dir + " at " + String.valueOf(wind);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateTime() {
        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(dt + timezone_offset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
        String formattedTimeString = ldt.format(dtf); // Thu Sep 30 10:06 PM, 2021

        return formattedTimeString;
    }

    public String getTime(long dt) {
        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(dt + timezone_offset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
        String formattedTimeString = ldt.format(dtf); // Thu Sep 30 10:06 PM, 2021

        return formattedTimeString;
    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }


    @Override
    public String toString() {
        return "CurrentWeather{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temp=" + temp +
                ", feelsLike=" + feelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", uvIndex=" + uvIndex +
                ", clouds=" + clouds +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", windDeg=" + windDeg +
                ", windGust=" + windGust +
                ", id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                '}';
    }
}
