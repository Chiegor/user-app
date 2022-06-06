package ru.app.userapp.model;

import ru.app.userapp.model.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

    public class User implements Serializable {
        private int id;
        private final String name;
        private final String cityWhereLived;
        private List<City> lived;
        private List<City> worked;

        public User (String name, String cityWhereLived){
            this.name = name;
            this.cityWhereLived = cityWhereLived;
            lived = new ArrayList<>();
            lived.add(new City(cityWhereLived));
            worked = new ArrayList<>();
        }

        public String getName (){
            return this.name;
        }

        // добавляет город где юзер жил
        public void addCityLived (String name) {
            this.lived.add(new City(name));
        }
        // добавляет город где юзер работал
        public void addCityWorked (String name) {
            this.worked.add(new City(name));
        }
        // получает список город где юзер жил
        public String getLived (){
            return " " + this.lived;
        }
        // получает список город где юзер работал
        public String getWorked () {
            return " " + this.worked;
        }

        public List<City> getListLivedCity (){
            return this.lived;
        }
        public List<City> getListWorkedCity (){
            return this.worked;
        }


        @Override
        public String toString() {
            return name + "\n" + "Lived: " + getLived() + "\n" + "Worked: " + getWorked();
        }
}
