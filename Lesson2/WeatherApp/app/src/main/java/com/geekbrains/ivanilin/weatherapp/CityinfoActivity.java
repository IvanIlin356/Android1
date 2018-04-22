package com.geekbrains.ivanilin.weatherapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CityinfoActivity extends AppCompatActivity {

    private TextView cityNameLabel;
    private String cityName;
    private TextView cityTempLabel;
    private Button showCityTempButton;
    private Button shareWithFriendButton;

    private static Random rnd = new Random();

    private int currentTemp;
    private boolean showPressure;
    private int weatherForecastType;
    private int currentPressure;
    private TextView cityPressureLabel;
    private RelativeLayout weekTempLayout;
    private TextView day1Date;
    private TextView day2Date;
    private TextView day3Date;
    private TextView day4Date;
    private TextView day5Date;
    private TextView day6Date;
    private TextView day7Date;
    private TextView day1Temp;
    private TextView day2Temp;
    private TextView day3Temp;
    private TextView day4Temp;
    private TextView day5Temp;
    private TextView day6Temp;
    private TextView day7Temp;
    private TextView day1Pressure;
    private TextView day2Pressure;
    private TextView day3Pressure;
    private TextView day4Pressure;
    private TextView day5Pressure;
    private TextView day6Pressure;
    private TextView day7Pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onCreate");
        setContentView(R.layout.activity_cityinfo);
        cityName = getIntent().getStringExtra(MainActivity.INTENT_CITY);
        showPressure = getIntent().getBooleanExtra(MainActivity.INTENT_SHOW_PRESSURE, false);
        weatherForecastType = getIntent().getIntExtra(MainActivity.INTENT_WEATHER_FORECAST, R.id.one_day_weather_forecast_radiobutton);

        initViews();

        setListeners();

        //showCityTempButton.callOnClick();
        showCityTempButton.performClick();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setListeners() {
        showCityTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTemp = (rnd.nextInt(20)) - 10;
                cityTempLabel.setText(getString(R.string.show_temp_template, currentTemp));

                if (showPressure) {
                    currentPressure = (rnd.nextInt(40)) + 740;
                    cityPressureLabel.setText(getString(R.string.show_pressure_template, currentPressure));
                }

                if (weatherForecastType == R.id.week_weather_forecast_radiobutton){
                    weekTempLoad();
                }

                Intent resultIntent = getIntent();
                resultIntent.putExtra(MainActivity.CURRENT_CITY_TEMP, currentTemp);
                setResult(RESULT_OK, resultIntent);
            }
        });

        shareWithFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_temp_template, cityName, currentTemp));
                startActivity(shareIntent);
            }
        });
    }

    private void weekTempLoad() {
        weekTempLayout.setVisibility(RelativeLayout.VISIBLE);
        //day1
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        currentTemp = (rnd.nextInt(20)) - 10;
        day1Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day1Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day1Pressure.setText(String.valueOf(currentPressure));
        }
        //day2
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        currentTemp = (rnd.nextInt(20)) - 10;
        day2Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day2Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day2Pressure.setText(String.valueOf(currentPressure));
        }

        //day3
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        currentTemp = (rnd.nextInt(20)) - 10;
        day3Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day3Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day3Pressure.setText(String.valueOf(currentPressure));
        }

        //day4
        calendar.add(Calendar.DAY_OF_YEAR, 4);
        currentTemp = (rnd.nextInt(20)) - 10;
        day4Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day4Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day4Pressure.setText(String.valueOf(currentPressure));
        }

        //day5
        calendar.add(Calendar.DAY_OF_YEAR, 5);
        currentTemp = (rnd.nextInt(20)) - 10;
        day5Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day5Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day5Pressure.setText(String.valueOf(currentPressure));
        }

        //day6
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        currentTemp = (rnd.nextInt(20)) - 10;
        day6Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day6Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day6Pressure.setText(String.valueOf(currentPressure));
        }

        //day7
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        currentTemp = (rnd.nextInt(20)) - 10;
        day7Date.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));
        day7Temp.setText(getString(R.string.show_temp_template_short, currentTemp));
        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            day7Pressure.setText(String.valueOf(currentPressure));
        }
    }

    private void initViews() {
        cityNameLabel = (TextView)findViewById(R.id.city_name_label);
        cityNameLabel.setText(cityName);

        cityTempLabel = (TextView)findViewById(R.id.city_temp_textview);
        cityPressureLabel = (TextView)findViewById(R.id.city_pressure_textview);

        showCityTempButton = (Button)findViewById(R.id.show_temp_button);
        shareWithFriendButton = (Button)findViewById(R.id.share_with_button);

        weekTempLayout = (RelativeLayout)findViewById(R.id.week_temperature_layout);

        day1Date = (TextView)findViewById(R.id.day_1_date);
        day2Date = (TextView)findViewById(R.id.day_2_date);
        day3Date = (TextView)findViewById(R.id.day_3_date);
        day4Date = (TextView)findViewById(R.id.day_4_date);
        day5Date = (TextView)findViewById(R.id.day_5_date);
        day6Date = (TextView)findViewById(R.id.day_6_date);
        day7Date = (TextView)findViewById(R.id.day_7_date);

        day1Temp = (TextView)findViewById(R.id.day_1_temp);
        day2Temp = (TextView)findViewById(R.id.day_2_temp);
        day3Temp = (TextView)findViewById(R.id.day_3_temp);
        day4Temp = (TextView)findViewById(R.id.day_4_temp);
        day5Temp = (TextView)findViewById(R.id.day_5_temp);
        day6Temp = (TextView)findViewById(R.id.day_6_temp);
        day7Temp = (TextView)findViewById(R.id.day_7_temp);

        day1Pressure = (TextView)findViewById(R.id.day_1_pressure);
        day2Pressure = (TextView)findViewById(R.id.day_2_pressure);
        day3Pressure = (TextView)findViewById(R.id.day_3_pressure);
        day4Pressure = (TextView)findViewById(R.id.day_4_pressure);
        day5Pressure = (TextView)findViewById(R.id.day_5_pressure);
        day6Pressure = (TextView)findViewById(R.id.day_6_pressure);
        day7Pressure = (TextView)findViewById(R.id.day_7_pressure);

    }

    @Override
    protected void onStart() {
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onResume");
        super.onResume();
    }



    @Override
    protected void onPause() {
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(MainActivity.LOG_TAG, "cityInfoActivity - onDestroy");
        super.onDestroy();
    }
}
