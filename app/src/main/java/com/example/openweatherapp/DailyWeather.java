package com.example.openweatherapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DailyWeather implements Serializable {

    private String timezone;
    private long timezone_offset;
    private long dt;
    private double tempDay;
    private double tempMin;
    private double tempMax;
    private double tempNight;
    private double tempEve;
    private double tempMorn;
//    weather -------------------
    private long id;
    private String description;
    private String icon;
//    ---------------------------
    private double pop;
    private double uvi;

    public DailyWeather(String timezone, long timezone_offset, long dt, double tempDay, double tempMin, double tempMax, double tempNight, double tempEve, double tempMorn, long id, String description, String icon, double pop, double uvi) {
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.dt = dt;
        this.tempDay = tempDay;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.tempNight = tempNight;
        this.tempEve = tempEve;
        this.tempMorn = tempMorn;
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.pop = pop;
        this.uvi = uvi;
    }


    public long getDt() {
        return dt;
    }

    public double getTempDay() {
        return tempDay;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempNight() {
        return tempNight;
    }

    public double getTempEve() {
        return tempEve;
    }

    public double getTempMorn() {
        return tempMorn;
    }

    public long getId() {
        return id;
    }

    public String getIcon() {
        return "_"+icon;
    }

    public double getPop() {
        return pop;
    }

    public double getUvi() {
        return uvi;
    }

    public String getTimezone() {
        return timezone;
    }

    public long getTimezone_offset() {
        return timezone_offset;
    }

    public String getDescription() {
        return description;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setTempDay(double tempDay) {
        this.tempDay = tempDay;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempNight(double tempNight) {
        this.tempNight = tempNight;
    }

    public void setTempEve(double tempEve) {
        this.tempEve = tempEve;
    }

    public void setTempMorn(double tempMorn) {
        this.tempMorn = tempMorn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setTimezone_offset(long timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay(long dt) {
        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(dt + timezone_offset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("EEEE, MM/dd", Locale.getDefault());
        String formattedTimeString = ldt.format(dtf);

        return formattedTimeString;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "dt=" + dt +
                ", tempDay=" + tempDay +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", tempNight=" + tempNight +
                ", tempEve=" + tempEve +
                ", tempMorn=" + tempMorn +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", pop=" + pop +
                ", uvi=" + uvi +
                '}';
    }
}


