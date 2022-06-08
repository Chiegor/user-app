package ru.app.userapp.service;

import ru.app.userapp.dao.UserDaoImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Service implements UserService {
    UserDaoImpl udl;

    public Service() throws SQLException {
        this.udl = new UserDaoImpl();
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
    public void getUserByName(String userName) throws SQLException {
        udl.getUserByName(userName);
    }

    @Override
    public void getAllUserFromCityLived(String cityName) throws SQLException {
        udl.getAllUserFromCityLived(cityName);
    }

    @Override
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        udl.getAllCityWhereUserLived(userName);
    }

    @Override
    public void getAllUserFromCityWorked(String cityName) throws SQLException {
        udl.getAllUserFromCityWorked(cityName);
    }

    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {
        udl.getAllCityWhereUserWorked(userName);
    }

    @Override
    public void createUser(String userName, String cityLived) throws SQLException {
        udl.createUser(userName, cityLived);
    }

    @Override
    public void updateUserByName(String userName) throws SQLException, IOException {
        udl.updateUserByName(userName);
    }

    @Override
    public void deleteUserByName(String userName) throws SQLException {
        udl.deleteUserByName(userName);
    }
}
