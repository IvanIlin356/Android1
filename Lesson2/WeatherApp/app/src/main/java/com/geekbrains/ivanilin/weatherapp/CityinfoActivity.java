package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CityinfoActivity extends AppCompatActivity {

    private TextView cityNameLabel;
    private String cityName;
    private TextView cityTempLabel;
    private Button shareWithFriendButton;

    private static Random rnd = new Random();

    private int currentTemp;
    private boolean showPressure;
    private int weatherForecastType;
    private int currentPressure;
    private TextView cityPressureLabel;
    private RecyclerView weekTempLayout;
    private WeekTempAdapter weekTempAdapter;

    private final Handler handler = new Handler();

    private JSONArray weatherArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onCreate");
        setContentView(R.layout.activity_cityinfo);
        cityName = getIntent().getStringExtra(MainFragment.INTENT_CITY);
        showPressure = getIntent().getBooleanExtra(MainFragment.INTENT_SHOW_PRESSURE, false);
        weatherForecastType = getIntent().getIntExtra(MainFragment.INTENT_WEATHER_FORECAST, R.id.one_day_weather_forecast_radiobutton);

        initViews();

        setListeners();

        //showCityTempButton.callOnClick();
        showTemp();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void showTemp() {
//        currentTemp = (rnd.nextInt(20)) - 10;
//        cityTempLabel.setText(getString(R.string.show_temp_template, currentTemp));

        updateWeatherData(cityName);

//        if (showPressure) {
//            currentPressure = (rnd.nextInt(40)) + 740;
//            cityPressureLabel.setText(getString(R.string.show_pressure_template, currentPressure));
//        }

    }

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject jsonObject = WeatherData.getJSONdata(getApplicationContext(), city);
                if (jsonObject == null){
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), getString(R.string.json_not_found), Toast.LENGTH_SHORT);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(jsonObject);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject jsonObject) {
        try {
            weatherArray = jsonObject.getJSONArray("list");
            // 1
            cityTempLabel.setText(getString(R.string.show_temp_template_short2,
                    weatherArray.getJSONObject(0).getJSONObject("main").getString("temp"),
                    weatherArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main")));
            if (showPressure){
                cityPressureLabel.setText(getString(R.string.show_pressure_template_short2, weatherArray.getJSONObject(0).getJSONObject("main").getString("pressure")));
            }

            if (weatherForecastType == R.id.week_weather_forecast_radiobutton) {
                weekTempLayout.setVisibility(RecyclerView.VISIBLE);
            }


            if (weatherForecastType == R.id.week_weather_forecast_radiobutton){
                weekTempLayout.setVisibility(RecyclerView.VISIBLE);

                weekTempAdapter = new WeekTempAdapter(new int[7], getApplicationContext(), weatherArray);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                weekTempLayout.setAdapter(weekTempAdapter);
                weekTempLayout.setLayoutManager(linearLayoutManager);

            }


        }
        catch (Exception e){

        }
    }

    private void setListeners() {
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

    static class WeekTempViewHolder extends RecyclerView.ViewHolder{
        TextView dayWeatherDate;
        TextView dayWeatherTemp;
        ImageView dayWeatherImage;
        TextView dayWeatherPressure;
        RelativeLayout dayWeatherLayout;


        public WeekTempViewHolder(View itemView) {
            super(itemView);
            dayWeatherDate = (TextView)itemView.findViewById(R.id.day_weather_list_item_date);
            dayWeatherTemp = (TextView)itemView.findViewById(R.id.day_weather_list_item_temp);
            dayWeatherImage = (ImageView)itemView.findViewById(R.id.day_weather_list_item_image);
            dayWeatherPressure = (TextView)itemView.findViewById(R.id.day_weather_list_item_pressure);
            dayWeatherLayout = (RelativeLayout)itemView.findViewById(R.id.day_weather_list_item);
        }
    }

    class WeekTempAdapter extends RecyclerView.Adapter<WeekTempViewHolder>{
        int[] days;
        Context context;
        Calendar calendar;
        JSONArray jsonArray;

        public WeekTempAdapter(int[] days, Context context, JSONArray jsonArray) {
            this.days = days;
            this.context = context;
            this.jsonArray = jsonArray;
        }

        @Override
        public WeekTempViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_weather_list_item, parent, false);
            return new WeekTempViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final WeekTempViewHolder holder, int position) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, holder.getAdapterPosition() + 1);
            holder.dayWeatherDate.setText(new SimpleDateFormat("dd/MM").format(calendar.getTime()));

            try {
                holder.dayWeatherTemp.setText(getString(
                        R.string.show_temp_template_short,
                        jsonArray.getJSONObject(holder.getAdapterPosition() + 1).getJSONObject("main").getString("temp")));

                if (showPressure) {
                    holder.dayWeatherPressure.setText(getString(
                            R.string.show_pressure_template_short2,
                            jsonArray.getJSONObject(holder.getAdapterPosition() + 1).getJSONObject("main").getString("pressure")));
                }


            switch (jsonArray.getJSONObject(holder.getAdapterPosition() + 1).getJSONArray("weather").getJSONObject(0).getString("icon")) {
                case "03d":
                case "04d":
                    holder.dayWeatherImage.setImageResource(R.drawable.cloudy1);
                    break;
                case "09d":
                case "10d":
                case "11d":
                    holder.dayWeatherImage.setImageResource(R.drawable.drizzle1);
                    break;
                case "02d":
                    holder.dayWeatherImage.setImageResource(R.drawable.mostly_cloudy1);
                    break;
                case "01d":
                    holder.dayWeatherImage.setImageResource(R.drawable.sunny1);
                    break;
            }

            }
            catch (Exception e)
            {
                Log.d("weatherData_getJson", "error", e);
            }

            holder.dayWeatherLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_popup_share_with:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_popup_temp_template,
                                            holder.dayWeatherDate.getText().toString(),
                                            cityName,
                                            holder.dayWeatherTemp.getText().toString()));
                                    startActivity(shareIntent);
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.inflate(R.menu.menu_popup_week);
                    popupMenu.show();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return days.length;
        }
    }

    private void initViews() {
        cityNameLabel = (TextView)findViewById(R.id.city_name_label);
        cityNameLabel.setText(cityName);

        cityTempLabel = (TextView)findViewById(R.id.city_temp_textview);
        cityPressureLabel = (TextView)findViewById(R.id.city_pressure_textview);

        shareWithFriendButton = (Button)findViewById(R.id.share_with_button);

        weekTempLayout = (RecyclerView) findViewById(R.id.week_temperature_recycle_layout);
    }
}
