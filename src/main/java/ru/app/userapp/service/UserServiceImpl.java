package ru.app.userapp.service;

import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class UserServiceImpl implements UserService {
    UserDaoImpl udl = new UserDaoImpl();

    public UserServiceImpl() {
        this.udl = Objects.requireNonNull(udl, "UserServiceImpl must be provided");
    }

    @Override
    public void getAllUsers() {
        udl.getAllUsers();
    }

    @Override
    public void getAllCities() {
        udl.getAllCities();
    }

    @Override
    public Long getUserIdByName(String userName) {
        return udl.getUserIdByName(userName);
    }

    @Override
    public void getAllUserByCityLived(String cityName) {
        udl.getAllUserByCityLived(cityName);
    }

    @Override
    public void getAllUserByCityWorked(String cityName) {
        udl.getAllUserByCityWorked(cityName);
    }

    @Override
    public void getAllCityWhereUserLived(String userName) {
        udl.getAllCityWhereUserLived(userName);
    }

    @Override
    public void getAllCityWhereUserWorked(String userName) {
        udl.getAllCityWhereUserWorked(userName);
    }

    @Override
    public Long createUser(User user) {
        return udl.createUser(user);
    }

    @Override
    public void updateByUserName(String userName) throws IOException {

        long userId = getUserIdByName(userName);

        System.out.println("Press 'l' for update cities where user lived or 'w' for worked");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();

        if (answer.equals("l")) {
            addCityWhereUserLived(userId);
        } else if (answer.equals("w")) {
            addCityWhereUserWorked(userId);
        }

    }

    @Override
    public void addCityWhereUserLived(long userId) throws IOException {
        System.out.println("Enter city where user lived: ");
        System.out.println("Enter 'done' when end");
        Set<String> setCity = new HashSet<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cityName;
        while (!(cityName = reader.readLine()).equals("done")) {
            setCity.add(cityName);
        }
        udl.addCityWhereUserLived(userId, setCity);
    }

    @Override
    public void addCityWhereUserWorked(long userId) throws IOException {
        System.out.println("Enter city where user worked: ");
        System.out.println("Enter 'done' when end");
        Set<String> setCity = new HashSet<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String cityName;
        while (!(cityName = reader.readLine()).equals("done")) {
            setCity.add(cityName);
        }
        udl.addCityWhereUserWorked(userId, setCity);
    }

    @Override
    public void deleteUserByName(String userName) {
        long userId = udl.getUserIdByName(userName);
        udl.deleteUser(userId);
    }

    @Override
    public void deleteUserById(long id) {
        udl.deleteUser(id);
    }
}
