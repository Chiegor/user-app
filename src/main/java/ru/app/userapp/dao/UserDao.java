package ru.app.userapp.dao;

import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDao {
    void getAllUsers() throws SQLException; // получить всех юзеров из базы
    void getAllCities() throws SQLException; // получить все города из базы

    void getUserByName(String userName) throws SQLException; // получить юзера по имени (города где жил, работал)

    void getAllUserByCityLived(String cityName) throws SQLException; // получить всех юзеров которые жили в городе N
    void getAllCityWhereUserLived(String userName) throws SQLException; // получить все города где жил юзер N

    void getAllUserByCityWorked(String cityName) throws SQLException; // получить всех юзеров которые работали в городе N
    void getAllCityWhereUserWorked(String userName) throws SQLException; // получить все города где работал юзер N

    Long createUser(User user) throws ApplicationException; // создать нового юзера
    void updateUserByName(String userName) throws SQLException, IOException; // обновить данные юзера (должен включать еще два метода - добавить где жил, работал)
    void deleteUserByName(String userName) throws SQLException; // удалить юзера по имени

    // эти методы вызываются из метода updateUserByName
    void addCityWhereUserLived(String userName, String cityName) throws SQLException;
    void addCityWhereUserWorked(String userName, String cityName) throws SQLException;
}
