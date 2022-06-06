package ru.app.userapp.model;

import java.io.Serializable;

public class City implements Serializable {
    private String cityName;

    public City (String cityName){
        this.cityName = cityName;
    }

    public String getCityName (){
        return this.cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }
}

