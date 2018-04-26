package com.geekbrains.ivanilin.weatherapp.db;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<String> cityList;

    public DataBase(){
        cityList = new ArrayList<String>();
        cityList.add("Москва");
        cityList.add("Берлин");
        cityList.add("Нью-Йорк");
        cityList.add("Токио");
        cityList.add("Прага");
        cityList.add("Лондон");
        cityList.add("Лима");
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }
}
