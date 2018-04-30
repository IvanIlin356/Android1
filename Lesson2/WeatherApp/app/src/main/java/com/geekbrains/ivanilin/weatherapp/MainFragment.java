package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.geekbrains.ivanilin.weatherapp.db.DataBase;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    public static final String PREF_NAME = "com.geekbrains.ivanilin.weatherapp_pref";
    public static final String PREF_LAST_CITY = "weatherapp_last_city";
    public static final String PREF_WEATHER_FORECAST = "weatherapp_weather_forecast";
    public static final String PREF_SHOW_PRESSURE = "weatherapp_show_pressure";

    public static final String INTENT_CITY = "weatherapp_intent_city";
    public static final String INTENT_WEATHER_FORECAST = "weatherapp_intent_weather_forecast";
    public static final String INTENT_SHOW_PRESSURE = "weatherapp_intent_show_pressure";

    public static final String CURRENT_CITY_TEMP = "currentCityTemp";
    public static final String CURRENT_CITY_TEMP2 = "currentCityTemp2";
    public static final int REQUEST_CODE = 103;

    public static final String LOG_TAG = "weatherAppLogTag";


    SharedPreferences sharedPreferences;

    private RadioGroup forecastTypeRadioGroup;
    private CheckBox showPressureCheckBox;
    private RecyclerView cityListRecyclerView;
    private CityListAdapter cityListAdapter;
    private DataBase dataBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(LOG_TAG, "mainActivity - onCreate");
//        setContentView(R.layout.fragment_main);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        dataBase = new DataBase();

        initViews(root);

        cityListAdapter = new CityListAdapter(dataBase.getCityList(), getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cityListRecyclerView.setAdapter(cityListAdapter);
        cityListRecyclerView.setLayoutManager(linearLayoutManager);


        loadPreferences();

//        if (savedInstanceState != null){
//            Log.d(LOG_TAG, "mainActivity - onSaveInsctanceState Read, value: " + savedInstanceState.getString(CURRENT_CITY_TEMP2));
//            showCurrentTemp(savedInstanceState.getString(CURRENT_CITY_TEMP2));
//        }

        setListeners();

        return root;
    }

    private void loadPreferences() {
        sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_WEATHER_FORECAST)){
            forecastTypeRadioGroup.check(sharedPreferences.getInt(PREF_WEATHER_FORECAST, R.id.one_day_weather_forecast_radiobutton));
        }

        if (sharedPreferences.contains(PREF_SHOW_PRESSURE)){
            showPressureCheckBox.setChecked(sharedPreferences.getBoolean(PREF_SHOW_PRESSURE, false));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        Log.d(LOG_TAG, "mainActivity - onSaveInsctanceState Save, value: " + currentCityTemp.getText().toString());
//        outState.putString(CURRENT_CITY_TEMP2, currentCityTemp.getText().toString());
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
//            if (data != null){
//            }
//        }
    }

    private void setListeners() {
        forecastTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(PREF_WEATHER_FORECAST, checkedId);
                editor.apply();
            }
        });

        showPressureCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_SHOW_PRESSURE, showPressureCheckBox.isChecked());
                editor.apply();
            }
        });

    }

    private void initViews(View root){
        cityListRecyclerView = (RecyclerView)root.findViewById(R.id.city_list_recycler_view);
        forecastTypeRadioGroup = (RadioGroup)root.findViewById(R.id.weather_forecast_type_radiogroup);
        forecastTypeRadioGroup.check(R.id.one_day_weather_forecast_radiobutton);
        showPressureCheckBox = (CheckBox)root.findViewById(R.id.show_pressure_checkbox);
    }

    // ==========  recycler ==========

    static class CityListViewHolder extends RecyclerView.ViewHolder{
        TextView cityItem;
        LinearLayout linearLayout;

        public CityListViewHolder(View itemView) {
            super(itemView);
            cityItem = itemView.findViewById(R.id.city_name_recycler_list);
            linearLayout = itemView.findViewById(R.id.linear_layout_recycler_view);
        }
    }

    class CityListAdapter extends RecyclerView.Adapter<CityListViewHolder>{
        ArrayList<String> cities;
        Context context;

        public CityListAdapter(ArrayList<String> cities, Context context) {
            this.cities = cities;
            this.context = context;
        }

        @Override
        public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item,
                    parent, false);
            return new CityListViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CityListViewHolder holder, int position) {
            String city = cities.get(holder.getAdapterPosition());
            holder.cityItem.setText(city);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { Intent intent = new Intent(context, CityinfoActivity.class);
                intent.putExtra(INTENT_CITY, cities.get(holder.getAdapterPosition()));
                intent.putExtra(INTENT_WEATHER_FORECAST, forecastTypeRadioGroup.getCheckedRadioButtonId());
                intent.putExtra(INTENT_SHOW_PRESSURE, showPressureCheckBox.isChecked());
                startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (cities != null && cities.size() != 0){
                return cities.size();
            }
            return 0;
        }
    }
}
