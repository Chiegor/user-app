package ru.app.userapp.dao;

import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public interface UserDao {
    void getAllUsers() throws SQLException; // получить всех юзеров из базы
    void getAllCities() throws SQLException; // получить все города из базы

    Long getUserIdByName(String userName) throws SQLException; // получить юзера по имени (города где жил, работал)

    void getAllUserByCityLived(String cityName) throws SQLException; // получить всех юзеров которые жили в городе N
    void getAllCityWhereUserLived(String userName) throws SQLException; // получить все города где жил юзер N

    void getAllUserByCityWorked(String cityName) throws SQLException; // получить всех юзеров которые работали в городе N
    void getAllCityWhereUserWorked(String userName) throws SQLException; // получить все города где работал юзер N

    Long createUser(User user) throws ApplicationException; // создать нового юзера
    void deleteUserByName(String userName) throws SQLException; // удалить юзера по имени

    // эти методы вызываются из метода updateUserByName
    void addCityWhereUserLived(long userId, Set<String> cityLived) throws SQLException;
    void addCityWhereUserWorked(long userId, Set<String> cityWorked) throws SQLException;

    void putCityIdUserIdInTable(long userId, long cityId, String tableName) throws SQLException;
    Long getCityId(String cityName) throws SQLException;
    Long addCityToTable(String cityName) throws SQLException;
}
