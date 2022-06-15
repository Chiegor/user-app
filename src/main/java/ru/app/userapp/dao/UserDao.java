package ru.app.userapp.dao;

import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public interface UserDao {
    void getAllUsers() throws SQLException;
    void getAllCities() throws SQLException;

    Long getUserIdByName(String userName) throws SQLException;

    void getAllUserByCityLived(String cityName) throws SQLException;
    void getAllCityWhereUserLived(String userName) throws SQLException;

    void getAllUserByCityWorked(String cityName) throws SQLException;
    void getAllCityWhereUserWorked(String userName) throws SQLException;

    Long createUser(User user) throws ApplicationException;
    void deleteUser(long id) throws SQLException;

    void addCityWhereUserLived(long userId, Set<String> cityLived) throws SQLException;
    void addCityWhereUserWorked(long userId, Set<String> cityWorked) throws SQLException;

    void putCityIdUserIdInTable(long userId, long cityId, String tableName) throws SQLException;
    Long getCityId(String cityName) throws SQLException;
}
