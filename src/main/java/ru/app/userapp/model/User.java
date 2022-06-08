package ru.app.userapp.model;

public class User {
    private String name;
    private String cityLived;

    public User(String name, String cityWhereLived) {
        this.name = name;
        this.cityLived = cityWhereLived;
    }

    public String getName() {
        return this.name;
    }

}
