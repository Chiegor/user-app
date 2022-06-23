package ru.app.userapp.service;

import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    void getAllUsers();
    void getAllCities();

    Long getUserIdByName(String userName);

    void getAllUserByCityLived(String cityName);
    void getAllCityWhereUserLived(String userName);

    void getAllUserByCityWorked(String cityName);
    void getAllCityWhereUserWorked(String userName);

    Long createUser(User user);
    void deleteUserByName(String userName);
    void deleteUserById(long id);

    void updateByUserName(String userName) throws IOException;

    void addCityWhereUserLived(long userId) throws IOException;
    void addCityWhereUserWorked(long userId) throws IOException;
}
