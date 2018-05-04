package com.geekbrains.ivanilin.weatherapp.db;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<String> cityList;

    public DataBase(){
        cityList = new ArrayList<String>();
        cityList.add("Moscow");
        cityList.add("Berlin");
        cityList.add("New York");
        cityList.add("Tokyo");
        cityList.add("London");
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }
}
