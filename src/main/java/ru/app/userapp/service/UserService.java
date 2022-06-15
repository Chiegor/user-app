package ru.app.userapp.service;

import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public interface UserService {
    void getAllUsers() throws SQLException;
    void getAllCities() throws SQLException;

    Long getUserIdByName(String userName) throws SQLException;

    void getAllUserByCityLived(String cityName) throws SQLException;
    void getAllCityWhereUserLived(String userName) throws SQLException;

    void getAllUserByCityWorked(String cityName) throws SQLException;
    void getAllCityWhereUserWorked(String userName) throws SQLException;

    Long createUser(User user) throws SQLException;
    void deleteUserByName(String userName) throws SQLException;
    void deleteUserById(long id) throws SQLException;

    void updateByUserId(long userId) throws SQLException, IOException;
    void updateByUserName(String userName) throws SQLException, IOException;

    void addCityWhereUserLived(long userId) throws SQLException, IOException;
    void addCityWhereUserWorked(long userId) throws SQLException, IOException;

    boolean validateUserInDB(String userName) throws SQLException;
    boolean validateCityInDB(String cityName) throws SQLException;

}
