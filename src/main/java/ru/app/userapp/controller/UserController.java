package ru.app.userapp.controller;

import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.exception.ValidationException;
import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface UserController {
    /**
     * Creates new user
     *
     * @param userName user name
     * @param citiesLived cities where user has lived
     * @param citiesWorked cities where user has worked
     * @return user id
     * @throws ValidationException in case of input parameters validation failures
     */
    long createUser(String userName, Set<String> citiesLived, Set<String> citiesWorked) throws ValidationException, SQLException;

    void getAllUsers() throws SQLException;
    void getAllCities() throws SQLException;

    Long getUserIdByName(String userName) throws SQLException;

    void getAllUserByCityLived(String cityName) throws SQLException;
    void getAllCityWhereUserLived(String userName) throws SQLException;

    void getAllUserByCityWorked(String cityName) throws SQLException;
    void getAllCityWhereUserWorked(String userName) throws SQLException;

    void updateByUserId(long userId) throws SQLException, IOException;
    void updateByUserName(String userName) throws SQLException, IOException;

    void deleteUserByName(String userName) throws SQLException;
    void deleteUserById(long id) throws SQLException;

    boolean validateUserInDB(String userName) throws SQLException;
    boolean validateCityInDB(String cityName) throws SQLException;
}
