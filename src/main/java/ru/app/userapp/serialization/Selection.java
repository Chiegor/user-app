package ru.app.userapp.serialization;

import ru.app.userapp.model.City;
import ru.app.userapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    // метод создает List<String> имен юзеров которые жили в городе cityName
    public List<String> ulc(List<User> list, String cityName) {
        List<String> nameList = new ArrayList<>();
        for (User user : list) {
            for (City city : user.getListLivedCity()) {
                if (city.getCityName().equals(cityName)) {
                    nameList.add(user.getName());
                }
            }
        }
        return nameList;
    }

    // метод создает List<String> имен юзеров которые работали в городе cityName
    public List<String> uwc(List<User> list, String cityName) {
        List<String> nameList = new ArrayList<>();
        for (User user : list) {
            for (City city : user.getListWorkedCity()) {
                if (city.getCityName().equals(cityName)) {
                    nameList.add(user.getName());
                }
            }
        }
        return nameList;
    }
}