package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

//    public void showPopup(View v){
//        PopupMenu popupMenu = new PopupMenu(this, v);
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menu_popup_share_with:
//                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setType("text/plain");
//                        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
//                        startActivity(shareIntent);
//                    return true;
//                }
//                return false;
//            }
//        });
//        popupMenu.inflate(R.menu.menu_popup_week);
//        popupMenu.show();
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void showTemp() {
        currentTemp = (rnd.nextInt(20)) - 10;
        cityTempLabel.setText(getString(R.string.show_temp_template, currentTemp));

        if (showPressure) {
            currentPressure = (rnd.nextInt(40)) + 740;
            cityPressureLabel.setText(getString(R.string.show_pressure_template, currentPressure));
        }

        if (weatherForecastType == R.id.week_weather_forecast_radiobutton){
            weekTempLayout.setVisibility(RecyclerView.VISIBLE);

            weekTempAdapter = new WeekTempAdapter(new int[7], getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            weekTempLayout.setAdapter(weekTempAdapter);
            weekTempLayout.setLayoutManager(linearLayoutManager);

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

        public WeekTempAdapter(int[] days, Context context) {
            this.days = days;
            this.context = context;
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

            currentTemp = (rnd.nextInt(20)) - 10;
            holder.dayWeatherTemp.setText(getString(R.string.show_temp_template_short, currentTemp));

            if (showPressure) {
                currentPressure = (rnd.nextInt(40)) + 740;
                holder.dayWeatherPressure.setText(getString(R.string.show_pressure_template_short, currentPressure));
            }

            switch (rnd.nextInt(4)) {
                case 0:
                    holder.dayWeatherImage.setImageResource(R.drawable.cloudy1);
                    break;
                case 1:
                    holder.dayWeatherImage.setImageResource(R.drawable.drizzle1);
                    break;
                case 2:
                    holder.dayWeatherImage.setImageResource(R.drawable.mostly_cloudy1);
                    break;
                case 3:
                    holder.dayWeatherImage.setImageResource(R.drawable.sunny1);
                    break;
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

    @Override
    protected void onStart() {
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onResume");
        super.onResume();
    }



    @Override
    protected void onPause() {
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(MainFragment.LOG_TAG, "cityInfoActivity - onDestroy");
        super.onDestroy();
    }
}
