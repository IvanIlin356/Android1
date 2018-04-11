package com.geekbrains.ivanilin.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class CityinfoActivity extends AppCompatActivity {

    private TextView cityNameLabel;
    private String cityName;
    private TextView cityTempLabel;
    private Button showCityTempButton;
    private Button shareWithFriendButton;

    private static Random rnd = new Random();

    private int currentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityinfo);
        cityName = getIntent().getStringExtra(MainActivity.INTENT_CITY);

        initViews();

        setListeners();

        //showCityTempButton.callOnClick();
        showCityTempButton.performClick();
    }

    private void setListeners() {
        showCityTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTemp = (rnd.nextInt(20)) - 10;
                cityTempLabel.setText(getString(R.string.show_temp_template, cityName, currentTemp));
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

    private void initViews() {
        cityNameLabel = (TextView)findViewById(R.id.city_name_label);
        cityNameLabel.setText(cityName);

        cityTempLabel = (TextView)findViewById(R.id.city_temp_textview);

        showCityTempButton = (Button)findViewById(R.id.show_temp_button);
        shareWithFriendButton = (Button)findViewById(R.id.share_with_button);
    }
}
