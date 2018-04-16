package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_NAME = "com.geekbrains.ivanilin.weatherapp_pref";
    public static final String PREF_LAST_CITY = "weatherapp_last_city";
    public static final String INTENT_CITY = "weatherapp_intent_city";
    public static final String CURRENT_CITY_TEMP = "currentCityTemp";
    public static final String CURRENT_CITY_TEMP2 = "currentCityTemp2";
    public static final int REQUEST_CODE = 103;

    public static final String LOG_TAG = "weatherAppLogTag";

    SharedPreferences sharedPreferences;

    Spinner citySpinner;
    Button showCityInfoButton;
    private Button callWrongIntentButton;
    private TextView tempSignTextView;
    private TextView currentCityTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "mainActivity - onCreate");
        setContentView(R.layout.activity_main);

        initViews();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_LAST_CITY)){
            citySpinner.setSelection(sharedPreferences.getInt(PREF_LAST_CITY, 0));
        }


        if (savedInstanceState != null){
            Log.d(LOG_TAG, "mainActivity - onSaveInsctanceState Read, value: " + savedInstanceState.getString(CURRENT_CITY_TEMP2));
            showCurrentTemp(savedInstanceState.getString(CURRENT_CITY_TEMP2));
        }

        setListeners();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "mainActivity - onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "mainActivity - onResume");
        super.onResume();
    }



    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "mainActivity - onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "mainActivity - onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "mainActivity - onDestroy");
        super.onDestroy();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, "mainActivity - onSaveInsctanceState Save, value: " + currentCityTemp.getText().toString());
        outState.putString(CURRENT_CITY_TEMP2, currentCityTemp.getText().toString());
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null){
                showCurrentTemp(String.valueOf(data.getIntExtra(CURRENT_CITY_TEMP, 0)));
            }
        }
    }

    private void showCurrentTemp(String currentTemp){
        tempSignTextView.setVisibility(TextView.VISIBLE);
        currentCityTemp.setVisibility(TextView.VISIBLE);
        currentCityTemp.setText(String.valueOf(currentTemp));
    }

    private void setListeners() {
        showCityInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = citySpinner.getSelectedItem().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(PREF_LAST_CITY, citySpinner.getSelectedItemPosition());
                editor.apply();

                Intent intent = new Intent(MainActivity.this, CityinfoActivity.class);
                intent.putExtra(INTENT_CITY, city);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        callWrongIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent wrongIntent = new Intent("someWrongAction");
                    wrongIntent.setType("text/plain");
                    wrongIntent.putExtra(Intent.EXTRA_TEXT, "someWrongAction");
                    startActivity(wrongIntent);
                }
                catch (Exception ex){
                    //Toast toast = new Toast(MainActivity.this);
                    Toast toast = Toast.makeText(MainActivity.this, "Такой Intent не найден", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //tempSignTextView.setVisibility(TextView.INVISIBLE);
                //currentCityTemp.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initViews(){
        citySpinner = (Spinner)findViewById(R.id.city_select_spinner);
        showCityInfoButton = (Button)findViewById(R.id.show_city_info_button);
        callWrongIntentButton = (Button)findViewById(R.id.сall_wrong_intent_button);
        tempSignTextView = (TextView)findViewById(R.id.temp_sign);
        currentCityTemp = (TextView)findViewById(R.id.current_city_temp);
    }
}
