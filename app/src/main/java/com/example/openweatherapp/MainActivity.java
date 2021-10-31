package com.example.openweatherapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String lat = "41.8675766";
    private String lon = "-87.616232";
    private String location = "Chicago";
    private boolean fahrenheit=true;
    private final ArrayList<HourlyWeather> hourly = new ArrayList<>();
    private final ArrayList<DailyWeather> daily = new ArrayList<>();

    private WeatherAdapter2 mAdapter2;
    private WeatherAdapter mAdapter;
    private RecyclerView recyclerView;
    private dayActivity da;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private boolean netStatus=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView cLocation = findViewById(R.id.locationBar);
        TextView cDateTime = findViewById(R.id.timeBar);


        recyclerView = findViewById(R.id.recycler);
//        Data to recycler
        mAdapter = new WeatherAdapter(hourly, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        doNetCheck();
        if (!netStatus) {
            cLocation.setText("");
            cDateTime.setText(R.string.noInt);
        } else {
//        while(!netStatus){
//            doNetCheck();
//            Log.d("Checking Network", "Still checking network");
//        }

            WeatherLoader loaderTaskRunnable = new WeatherLoader(this, fahrenheit, lat, lon);
            new Thread(loaderTaskRunnable).start();
        }
    }

//    Attaches option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    menu item task handler
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (!netStatus){
            Toast.makeText(MainActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();

        }else {
            if (item.getItemId() == R.id.menuCalendar) {
                Log.d(TAG, "Calendar Button Selected");
                Intent intent = new Intent(this, dayActivity.class);
                intent.putExtra("dailyWeather", new DataWrapper(daily));
                intent.putExtra("location", getLocationName(location));
                intent.putExtra("unit", getFahrenheit());
                startActivity(intent);
            } else if (item.getItemId() == R.id.menuUnit) {
                Log.d(TAG, "Unit Button Selected");
                fahrenheit = !fahrenheit;
                daily.clear();
                hourly.clear();
                WeatherLoader loaderTaskRunnable = new WeatherLoader(this, fahrenheit, lat, lon);
                new Thread(loaderTaskRunnable).start();
                invalidateOptionsMenu(); //cause a redraw
            } else if (item.getItemId() == R.id.menuLocation) {
                Log.d(TAG, "Location Button Selected");
                locationChange();
                daily.clear();
                hourly.clear();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (fahrenheit) {
            menu.findItem(R.id.menuUnit)
                    .setIcon(R.drawable.units_f);
        } else {
            menu.findItem(R.id.menuUnit)
                    .setIcon(R.drawable.units_c);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void handleError(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data Problem")
                .setMessage(s)
                .setPositiveButton("OK", (dialogInterface, i) -> {})
                .create().show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateData(CurrentWeather cw, HourlyWeather[] hw, DailyWeather[] dw) {

        String unit = "°F";
        if (!fahrenheit){
            unit = "°C";
        }
//        Current Data
        TextView cLocation = findViewById(R.id.locationBar);
        TextView cDateTime = findViewById(R.id.timeBar);
        TextView cTemp = findViewById(R.id.temperature);
        TextView cFeelsLike = findViewById(R.id.feelsLike);
        TextView cHum = findViewById(R.id.humidity);
        TextView cUvi = findViewById(R.id.uvIndex);
        TextView cDescript = findViewById(R.id.weatherDescription);
        TextView cWind = findViewById(R.id.winds);
        TextView cVis = findViewById(R.id.visibility);
        TextView cSunrise = findViewById(R.id.sunrise);
        TextView cSunset = findViewById(R.id.sunset);
        ImageView cImg = findViewById(R.id.imageView);
        TextView mornL = findViewById(R.id.morningLabel);
        TextView dayL = findViewById(R.id.daytimeLabel);
        TextView eveningL = findViewById(R.id.eveningLabel);
        TextView nightL = findViewById(R.id.nightLabel);

        cLocation.setText(getLocationName(location));
        cDateTime.setText(cw.getDateTime());
        cTemp.setText(String.valueOf(Math.round(cw.getTemp()))+unit);
        cFeelsLike.setText("Feels Like "+String.valueOf(Math.round(cw.getFeelsLike()))+unit);
        cHum.setText("Humidity: "+String.valueOf(cw.getHumidity())+"%");
        cUvi.setText("UV Index: "+String.valueOf(Math.round(cw.getUvIndex())));
        cDescript.setText(cw.getDescription());
        cWind.setText("Winds: " +cw.getWind() +" mph");
        cVis.setText("Visibility: " +String.valueOf(cw.getVisibility()) +" mi");
        cSunrise.setText("Sunrise: "+cw.getTime(cw.getSunrise()));
        cSunset.setText("Sunset: "+cw.getTime(cw.getSunset()));
        cImg.setImageResource(getImg(cw.getIcon()));
        mornL.setText(R.string._8am);
        dayL.setText(R.string._1pm);
        eveningL.setText(R.string._5pm);
        nightL.setText(R.string._11pm);

//        Hourly Data
        hourly.addAll(Arrays.asList(hw));
        Log.d("Hourly", String.valueOf(hourly));
//        Daiy Data

        daily.addAll(Arrays.asList(dw));
        Log.d("Daily", String.valueOf(daily));
        TextView morn = findViewById(R.id.morningTemp);
        TextView day = findViewById(R.id.daytimeTemp);
        TextView evening = findViewById(R.id.EveningTemp);
        TextView night = findViewById(R.id.NightTemp);

        morn.setText(String.valueOf(Math.round(dw[0].getTempMorn()))+unit);
        day.setText(String.valueOf(Math.round(dw[0].getTempDay()))+unit);
        evening.setText(String.valueOf(Math.round(dw[0].getTempEve()))+unit);
        night.setText(String.valueOf(Math.round(dw[0].getTempNight()))+unit);


        mAdapter.notifyDataSetChanged();
//        mAdapter2.notifyDataSetChanged();
    }

    public int getImg(String img){
        int iconResId = this.getResources().getIdentifier(img,
                "drawable", this.getPackageName());

        return iconResId;
    }

    private String getLocationName(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this); // Here, “this” is an Activity
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            String locale = p1 + ", " + p2;
            return locale;
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }

    private void getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this); // Here, “this” is an Activity
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned
            }
            lat = String.valueOf(address.get(0).getLatitude());
            lon = String.valueOf(address.get(0).getLongitude());

        } catch (IOException e) {
            // Failure to get an Address object
        }
    }


    public void locationChange() {
        // Single input value dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create an edittext and set it to be the builder's view
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);

//        builder.setIcon(R.drawable.icon1);

        // lambda can be used here (as is below)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                location = getLocationName(String.valueOf(et.getText()));
                getLatLon(location);
                run();
            }
        });
        // lambda can be used here (as is below)
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, "Location was not changed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setMessage("For US locations, enter as 'City' or 'City, State'\n\nFor international locations, enter as 'City, Country'");
        builder.setTitle("Enter A Location");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void run(){
        WeatherLoader loaderTaskRunnable = new WeatherLoader(this, fahrenheit, lat, lon);
        new Thread(loaderTaskRunnable).start();
    }
    public boolean getFahrenheit() {
        return fahrenheit;
    }

    private void doNetCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            return;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        boolean isMetered = cm.isActiveNetworkMetered();

        String metered =
                isMetered ? "\nConnection is metered" : "\nConnection is not metered";

        if (!isConnected) {
            netStatus=false;
        }
    }
}