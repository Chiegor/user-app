package ru.app.userapp.controller;

import ru.app.userapp.exception.ValidationException;

import java.io.IOException;
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
    long createUser(String userName, Set<String> citiesLived, Set<String> citiesWorked) throws ValidationException;

    void getAllUsers();
    void getAllCities();

    Long getUserIdByName(String userName);

    void getAllUserByCityLived(String cityName);
    void getAllCityWhereUserLived(String userName);

    void getAllUserByCityWorked(String cityName);
    void getAllCityWhereUserWorked(String userName);

    void updateByUserName(String userName) throws IOException;

    void deleteUser(String userName);
}
