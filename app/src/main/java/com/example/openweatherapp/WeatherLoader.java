package com.example.openweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class WeatherLoader implements Runnable {

    private static final String TAG = "WeatherDownloadRunnable";

    private final MainActivity mainActivity;
    private String lat;
    private String lon;
    private final boolean fahrenheit;

    private static final String weatherURL = "https://api.openweathermap.org/data/2.5/onecall?";
    private static final String iconUrl = "https://openweathermap.org/img/w/";

    //////////////////////////////////////////////////////////////////////////////////
    // Sign up to get your API Key at:  https://home.openweathermap.org/users/sign_up
    private static final String yourAPIKey = "ad0e27395774b834fc487f4498776782";
    //
    //////////////////////////////////////////////////////////////////////////////////

    WeatherLoader(MainActivity mainActivity, boolean fahrenheit, String lat, String lon) {
        this.mainActivity = mainActivity;
        this.lat = lat;
        this.lon = lon;
        this.fahrenheit = fahrenheit;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();

        buildURL.appendQueryParameter("lat", lat);
        buildURL.appendQueryParameter("lon", lon);
        buildURL.appendQueryParameter("units", (fahrenheit ? "imperial" : "metric"));
        buildURL.appendQueryParameter("appid", yourAPIKey);
        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("exclude", "minutely");
        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                InputStream is = connection.getErrorStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                handleError(sb.toString());
                return;
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            handleResults(null);
            return;
        }
        handleResults(sb.toString());
    }

    public void handleError(String s) {
        String msg = "Error: ";
        try {
            JSONObject jObjMain = new JSONObject(s);
            msg += jObjMain.getString("message");

        } catch (JSONException e) {
            msg += e.getMessage();
        }

        String finalMsg = String.format("%s (%s)", msg);
        mainActivity.runOnUiThread(() -> mainActivity.handleError(finalMsg));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void handleResults(final String jsonString) {

        final CurrentWeather cw = parseCurrent(jsonString);
        final HourlyWeather[] hw = parseHourly(jsonString);
        final DailyWeather[] dw = parseDaily(jsonString);

        mainActivity.runOnUiThread(() -> mainActivity.updateData(cw, hw, dw));
    }

    private CurrentWeather parseCurrent(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);
            Log.d("Testing:", "Pass");

            double lat = jObjMain.getDouble("lat");
            double lon = jObjMain.getDouble("lon");
            String timezone = jObjMain.getString("timezone");
            long timezone_offset = jObjMain.getLong("timezone_offset");

//            Current vars
            long cdt;
            long csunrise;
            long csunset;
            double ctemp;
            double cfeelsLike;
            long cpressure;
            long chumidity;
            double cuvIndex;
            long cclouds;
            long cvisibility;
            double cwindSpeed;
            long cwindDeg;
            double cwindGust = -1;
            String crain = "NULL";
            String csnow = "NULL";
            String cicon;

            // current - weather
            long cid;
            String cmain;
            String cdescription;

//          // Current section
            JSONObject current = jObjMain.getJSONObject("current");
            JSONArray currentWeather = current.getJSONArray("weather");
            JSONObject JcurrentWeather = (JSONObject) currentWeather.get(0);

            cdt = current.getLong("dt");
            csunrise = current.getLong("sunrise");
            csunset = current.getLong("sunset");
            ctemp = current.getDouble("temp");
            cfeelsLike = current.getDouble("feels_like");
            cpressure = current.getLong("pressure");
            chumidity = current.getLong("humidity");
            cuvIndex = current.getDouble("uvi");
            cclouds = current.getLong("clouds");
            cvisibility = current.getLong("visibility");
            cwindSpeed = current.getDouble("wind_speed");
            cwindDeg = current.getLong("wind_deg");
            try{
                cwindGust = current.getDouble("wind_gust");
            } catch(Exception e){}
           try{
                crain = current.getString("rain");
            } catch(Exception e){}
            try{
                csnow = current.getString("snow");
            } catch(Exception e){}

            cid = JcurrentWeather.getLong("id");
            cmain = JcurrentWeather.getString("main");
            cdescription = JcurrentWeather.getString("description");
            cicon = JcurrentWeather.getString("icon");

//            Current constructor
            return new CurrentWeather(lat, lon, timezone, timezone_offset, cdt, csunrise, csunset, ctemp, cfeelsLike, cpressure, chumidity, cuvIndex, cclouds, cvisibility, cwindSpeed, cwindDeg, cwindGust, cid, cmain, cdescription, cicon, crain, csnow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private HourlyWeather[] parseHourly(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);
            Log.d("Testing:", "Pass");
            String timezone = jObjMain.getString("timezone");
            long timezone_offset = jObjMain.getLong("timezone_offset");

//            Hourly vars
            long cdt;
            double ctemp;
            String cdescription;
            String cicon;
            double cpop;

//          // Hourly section
            JSONArray hourlyArray = jObjMain.getJSONArray("hourly");
            HourlyWeather[] hourly = new HourlyWeather[hourlyArray.length()];

            for (int i = 0; i < hourlyArray.length(); i++) {
                JSONObject h = hourlyArray.getJSONObject(i);
                JSONArray weather = h.getJSONArray("weather");
                JSONObject Hweather = (JSONObject) weather.get(0);

                cdt = h.getLong("dt");
                ctemp = h.getDouble("temp");
                cpop = h.getDouble("pop");

                cdescription = Hweather.getString("description");
                cicon = Hweather.getString("icon");

                hourly[i] = new HourlyWeather(timezone, timezone_offset, cdt, ctemp, cdescription, cicon, cpop);
            }
            return hourly;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private DailyWeather[] parseDaily(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);
            Log.d("Testing:", "Pass");
            String timezone = jObjMain.getString("timezone");
            long timezone_offset = jObjMain.getLong("timezone_offset");

//            Daily vars
            long cdt;
            double ctempDay;
            double ctempMin;
            double ctempMax;
            double ctempNight;
            double ctempEve;
            double ctempMorn;
            long cid;
            String cdescription;
            String cicon;
            double cpop;
            double cuvi;

//          // Daily section
            JSONArray dailyArray = jObjMain.getJSONArray("daily");
            DailyWeather[] daily = new DailyWeather[dailyArray.length()];

            for (int i = 0; i < dailyArray.length(); i++) {
                JSONObject d = dailyArray.getJSONObject(i);
                JSONObject dTemp = d.getJSONObject("temp");
                JSONArray weather = d.getJSONArray("weather");
                JSONObject Dweather = (JSONObject) weather.get(0);

                cdt = d.getLong("dt");
                cpop = d.getDouble("pop");
                cuvi = d.getDouble("uvi");

                ctempDay = dTemp.getDouble("day");
                ctempMin = dTemp.getDouble("min");
                ctempMax = dTemp.getDouble("max");
                ctempNight = dTemp.getDouble("night");
                ctempEve = dTemp.getDouble("eve");
                ctempMorn = dTemp.getDouble("morn");

                cid = Dweather.getLong("id");
                cdescription = Dweather.getString("description");
                cicon = Dweather.getString("icon");

                daily[i] = new DailyWeather(timezone, timezone_offset, cdt, ctempDay, ctempMin, ctempMax, ctempNight, ctempEve, ctempMorn, cid, cdescription, cicon, cpop, cuvi);
            }
            return daily;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
