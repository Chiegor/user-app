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
    long createUser(String userName, Set<String> citiesLived, Set<String> citiesWorked) throws ValidationException;

    void getAllUsers() throws SQLException; // получить всех юзеров из базы
    void getAllCities() throws SQLException; // получить все города из базы

    Long getUserIdByName(String userName) throws SQLException; // получить юзера по имени (города где жил, работал)

    void getAllUserByCityLived(String cityName) throws SQLException; // получить всех юзеров которые жили в городе N
    void getAllCityWhereUserLived(String userName) throws SQLException; // получить все города где жил юзер N

    void getAllUserByCityWorked(String cityName) throws SQLException; // получить всех юзеров которые работали в городе N
    void getAllCityWhereUserWorked(String userName) throws SQLException; // получить все города где работал юзер N

    void updateByUserId(long userId) throws SQLException, IOException;
    void updateByUserName(String userName) throws SQLException, IOException;

    void deleteUserByName(String userName) throws SQLException; // удалить юзера по имени

    boolean validateUserInDB(String userName) throws SQLException;
}
