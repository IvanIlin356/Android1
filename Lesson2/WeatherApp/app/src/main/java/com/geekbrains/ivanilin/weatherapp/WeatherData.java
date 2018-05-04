package com.geekbrains.ivanilin.weatherapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData {

    //private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric";
    private static final String KEY_NAME = "x-api-key";
    private static final String API_KEY = "2bcc2b628b3d3a3ae459b1f80f45b63e";
    private static final String RESPONSE = "cod";
    private static final String NEW_LINE = "\n";
    private static final int ALL_GOOD = 200;
    public static final String WEATHER_DATA_GET_JSON = "weatherData_getJson";

    public static JSONObject getJSONdata(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty(KEY_NAME, API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVar;

            while ((tempVar = reader.readLine()) != null) {
                rawData.append(tempVar).append(NEW_LINE);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());

            if (jsonObject.getInt(RESPONSE) != ALL_GOOD){
                return null;
            }
            return jsonObject;
        }
        catch (Exception e) {
            Log.d(WEATHER_DATA_GET_JSON, "error", e);
            return null;
        }

    }
}
