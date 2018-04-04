package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_NAME = "com.geekbrains.ivanilin.weatherapp_pref";
    public static final String PREF_LAST_CITY = "weatherapp_last_city";
    SharedPreferences sharedPreferences;

    Random rnd;
    Spinner citySpinner;
    Button showTempButton;
    TextView showTempTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        initViews();

        if (sharedPreferences.contains(PREF_LAST_CITY)){
            citySpinner.setSelection(sharedPreferences.getInt(PREF_LAST_CITY, 0));
        }

        showTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = citySpinner.getSelectedItem().toString();
                showTempTextView.setText(getString(R.string.show_temp_textview, city, (rnd.nextInt(20) - 10)));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(PREF_LAST_CITY, citySpinner.getSelectedItemPosition());
                editor.apply();
            }
        });
    }

    private void initViews(){
        citySpinner = (Spinner)findViewById(R.id.city_select_spinner);
        showTempButton = (Button)findViewById(R.id.show_temp_button);
        showTempTextView = (TextView)findViewById(R.id.city_temp_textview);
        rnd = new Random();
    }
}
