package ru.app.userapp.service;

import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

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
    public void getUserByName(String userName) throws SQLException {
        udl.getUserByName(userName);
    }

    @Override
    public void getAllUserByCityLived(String cityName) throws SQLException {
        udl.getAllUserByCityLived(cityName);
    }

    @Override
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        udl.getAllCityWhereUserLived(userName);
    }

    @Override
    public void getAllUserByCityWorked(String cityName) throws SQLException {
        udl.getAllUserByCityWorked(cityName);
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
    public void updateUserByName(String userName) throws SQLException, IOException {
        udl.updateUserByName(userName);
    }

    @Override
    public void deleteUserByName(String userName) throws SQLException {
        udl.deleteUserByName(userName);
    }
}
