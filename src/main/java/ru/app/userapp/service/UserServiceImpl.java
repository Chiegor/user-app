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

    public UserServiceImpl() throws SQLException {
        this.udl = Objects.requireNonNull(udl, "UserServiceImpl must be provided");
    }

    @Override
    public void getAllUsers() throws SQLException {
        udl.getAllUsers();
    }

    @Override
    public void getAllCities() throws SQLException {
        udl.getAllCities();
    }

    @Override
    public Long getUserIdByName(String userName) throws SQLException {
        return udl.getUserIdByName(userName);
    }

    @Override
    public void getAllUserByCityLived(String cityName) throws SQLException {
        udl.getAllUserByCityLived(cityName);
    }

    @Override
    public void getAllUserByCityWorked(String cityName) throws SQLException {
        udl.getAllUserByCityWorked(cityName);
    }

    @Override
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        udl.getAllCityWhereUserLived(userName);
    }

    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {
        udl.getAllCityWhereUserWorked(userName);
    }

    @Override
    public Long createUser(User user) throws SQLException {
        return udl.createUser(user);
    }

    @Override
    public void updateByUserId(long userId) throws SQLException, IOException {
        System.out.println("Press 'l' for enter city where user lived");
        System.out.println("or press 'w' for enter city where user worked");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();

        if (answer.equals("l")) {
            addCityWhereUserLived(userId);
        } else if (answer.equals("w")) {
            addCityWhereUserWorked(userId);
        }
    }

    @Override
    public void updateByUserName(String userName) throws SQLException, IOException {

        long userId = getUserIdByName(userName);

        System.out.println("Press 'l' for enter city where user lived");
        System.out.println("or press 'w' for enter city where user worked");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();

        if (answer.equals("l")) {
            addCityWhereUserLived(userId);
        } else if (answer.equals("w")) {
            addCityWhereUserWorked(userId);
        }

    }

    @Override
    public void addCityWhereUserLived(long userId) throws SQLException, IOException {
        System.out.println("Enter city where user lived: ");
        System.out.println("Enter 'done' when end");
        Set<String> setCity = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String cityName;
            while (!(cityName = reader.readLine()).equals("done")) {
                setCity.add(cityName);
            }
        }
        udl.addCityWhereUserLived(userId, setCity);
    }

    @Override
    public void addCityWhereUserWorked(long userId) throws SQLException, IOException {
        System.out.println("Enter city where user worked: ");
        System.out.println("Enter 'done' when end");
        Set<String> setCity = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String cityName;
            while (!(cityName = reader.readLine()).equals("done")) {
                setCity.add(cityName);
            }
        }
        udl.addCityWhereUserWorked(userId, setCity);
    }



    @Override
    public void deleteUserByName(String userName) throws SQLException {
        udl.deleteUserByName(userName);
    }

    @Override
    public boolean validateUserInDB(String userName) throws SQLException {
        if (getUserIdByName(userName) == 0L) {
            return false;
        } else return true;
    }
}
