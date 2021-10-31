package com.example.openweatherapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HourlyWeather implements Serializable {

    private String timezone;
    private long timezone_offset;
    private long dt;
    private double temp;
    //    weather -------------------
    private String description;
    private String icon;
    //    ---------------------------
    private double pop;


    public HourlyWeather(String timezone, long timezone_offset, long dt, double temp, String description, String icon, double pop) {
        this.timezone = timezone;
        this.timezone_offset = timezone_offset;
        this.dt = dt;
        this.temp = temp;
        this.description = description;
        this.icon = icon;
        this.pop = pop;
    }


    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return temp;
    }

    public String getIcon() {
        return "_"+icon;
    }

    public String getDescription() {
        return description;
    }

    public double getPop() {
        return pop;
    }

    public String getTimezone() {
        return timezone;
    }

    public long getTimezone_offset() {
        return timezone_offset;
    }

    public void setDescription(String description) {this.description = description;}

    public void setDt(long dt) {
        this.dt = dt;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setTimezone_offset(long timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public String getTime(long dt) {
        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(dt + timezone_offset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
        String formattedTimeString = ldt.format(dtf);

        return formattedTimeString;
    }

    public String getDay(long dt) {
        LocalDateTime ldt =
                LocalDateTime.ofEpochSecond(dt + timezone_offset, 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        String formattedTimeString = ldt.format(dtf);

        if (formattedTimeString.toUpperCase(Locale.ROOT).equals(LocalDate.now().getDayOfWeek().name())){
            return "Today";
        }

        return formattedTimeString;
    }

    @Override
    public String toString() {
        return "HourlyWeather{" +
                "dt='" + dt + '\'' +
                ", temp='" + temp + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", pop='" + pop + '\'' +
                '}';
    }
}
