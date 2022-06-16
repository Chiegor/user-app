package ru.app.userapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.PropertiesProvider;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.DBC;
import ru.app.userapp.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final Connection connection;

    public UserDaoImpl() throws SQLException { // прочитать properties из PV
        String url = PropertiesProvider.getProperties().getProperty("datasource.url");
        String username = PropertiesProvider.getProperties().getProperty("datasource.username");
        String password = PropertiesProvider.getProperties().getProperty("datasource.password");

        connection = DriverManager.getConnection(url, username, password);
        log.info("db connection created");

    }
    // #1
    @Override
    public void getAllUsers() throws SQLException {
        String query = "select * from " + DBC.U_TABLE + " order by " + DBC.U_USER_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.U_USER_ID) + " " + resultSet.getString(DBC.U_USER_NAME));
        }
    }

    // #2
    @Override
    public void getAllCities() throws SQLException {
        String query = "select * from " + DBC.C_TABLE + " order by " + DBC.C_CITY_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.C_CITY_ID) + " " + resultSet.getString(DBC.C_CITY_NAME));
        }
    }

    // #3
    @Override
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        String query = "select distinct " + DBC.C_CITY_NAME +
                " from " + DBC.U_TABLE +
                " join " + DBC.UCL_TABLE +
                " on " + DBC.U_USER_ID + " = " + DBC.UCL_USER_ID +
                " and " + DBC.U_USER_NAME + " = '" + userName + "'" +
                " join " + DBC.C_TABLE +
                " on " + DBC.C_CITY_ID + " = " + DBC.UCL_CITY_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.C_CITY_NAME));
        }
    }
    // #4
    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {
        String query = "select distinct " + DBC.C_CITY_NAME +
                " from " + DBC.U_TABLE +
                " join " + DBC.UCW_TABLE +
                " on " + DBC.U_USER_ID + " = " + DBC.UCW_USER_ID +
                " and " + DBC.U_USER_NAME + " = '" + userName + "' " +
                " join " + DBC.C_TABLE +
                " on " + DBC.C_CITY_ID + " = " + DBC.UCW_CITY_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.C_CITY_NAME));
        }
    }

    // #5
    @Override
    public Long createUser(User user) throws ApplicationException {
        String userName = user.getName();
        Set<String> cityLived = user.getCitiesLived();
        Set<String> cityWorked = user.getCitiesWorked();
        long userId;
        
        try {
            String query = "INSERT INTO " + DBC.U_TABLE + " (" + DBC.USER_NAME + ") Values ('" + userName + "')";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            log.debug("creating new user {}", user);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {throw new ApplicationException("Creating user failed, no rows affected.");}

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                    log.debug("created user id=" + userId);
                    System.out.println("User id is: " + userId);
                } else {
                    throw new ApplicationException("Creating user failed, no ID obtained.");
                }
            }

            for (String city : cityLived) {
                long cityId = getCityId(city);
                if (cityId != 0L) {
                    putCityIdUserIdInTable(userId, cityId, DBC.UCL_TABLE);
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, DBC.UCL_TABLE);
                }
            }

            for (String city : cityWorked) {
                long cityId = getCityId(city);
                if (cityId != 0L) {
                    putCityIdUserIdInTable(userId, cityId, DBC.UCW_TABLE);
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, DBC.UCW_TABLE);
                }
            }
            System.out.println("User " + userName + " add");

        } catch (SQLException e){
            throw new ApplicationException("error creating user in db", e);
        }
        return userId;

    }
    public Long createCity(String cityName) throws SQLException {
        long cityId;
        try {
            String query = "insert into " + DBC.C_TABLE + " (" + DBC.CITY_NAME + ") values ('" + cityName + "')";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            log.debug("creating new city {}", cityName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new ApplicationException("Creating city failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cityId = generatedKeys.getLong(1);
                    log.debug("created city id=" + cityId);
                    System.out.println(cityName + " id is: " + cityId);
                } else {
                    throw new ApplicationException("Creating city failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new ApplicationException("error creating city in db", e);
        }
        return cityId;
    }

    @Override
    public void putCityIdUserIdInTable(long userId, long cityId, String tableName) throws SQLException {
        try {
            String query = "insert into " + tableName + " (" +
                    DBC.USER_ID + ", " + DBC.CITY_ID + ") " +
                    "values (" + userId + ", " + cityId + ")";
            PreparedStatement statement = connection.prepareStatement(query);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {throw new ApplicationException("putCityIdUserIdInTable error");}
            statement.close();
        } catch (SQLException e) {
            throw  new ApplicationException("ERROR put City Id User Id In Table", e);
        }
    }

    @Override
    public Long getCityId(String cityName) throws SQLException {
        long id = 0L;
        String query = "select " + DBC.CITY_ID + " from " + DBC.C_TABLE + " where " + DBC.CITY_NAME + " = '" + cityName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            id = resultSet.getLong(DBC.C_CITY_ID);
        }
        return id;
    }

    // #6
    @Override
    public Long getUserIdByName(String userName) throws SQLException {
        long id = 0L;
        String query = "select " + DBC.USER_ID + " from " + DBC.U_TABLE + " where " + DBC.USER_NAME + " = '" + userName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            id = resultSet.getLong(DBC.U_USER_ID);
        }

        if (id == 0L) {
            System.out.println("User " + userName + " not found ");
            System.exit(123);
            return null;
        } else {
            return id;
        }
    }

    @Override
    public void getAllUserByCityLived(String cityName) throws SQLException {
        String query = "select distinct " + DBC.U_USER_NAME +
                " from " + DBC.C_TABLE +
                " join " + DBC.UCL_TABLE +
                " on " + DBC.C_CITY_ID + " = " + DBC.UCL_CITY_ID +
                " and " + DBC.C_CITY_NAME +" = '" + cityName + "' " +
                " join " + DBC.U_TABLE +
                " on " + DBC.U_USER_ID + " = " + DBC.UCL_USER_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.U_USER_NAME));
        }
    }

    @Override
    public void getAllUserByCityWorked(String cityName) throws SQLException {
        String query = "select distinct " + DBC.U_USER_NAME +
                " from " + DBC.C_TABLE +
                " join " + DBC.UCW_TABLE +
                " on " + DBC.C_CITY_ID + " = " + DBC.UCW_CITY_ID +
                " and " + DBC.C_CITY_NAME + " = '" + cityName + "' " +
                " join " + DBC.U_TABLE +
                " on " + DBC.U_USER_ID + " = " + DBC.UCW_USER_ID;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(DBC.U_USER_NAME));
        }
    }

    @Override
    public void deleteUser(long id) throws SQLException {
        String query = "delete from " + DBC.U_TABLE + " where " + DBC.U_USER_ID + " = " + id + "";
        Statement statement = connection.createStatement();
        int affectedRows = statement.executeUpdate(query);
        if (affectedRows == 0) {
            throw new ApplicationException("Delete user failed, no rows affected.");
        } else {
            System.out.println("User deletion was successful");
        }
    }

    @Override
    public void addCityWhereUserLived(long userId, Set<String> cityLived) throws SQLException {
        long cityId;
        for (String city : cityLived) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, DBC.UCL_TABLE);
        }
    }

    @Override
    public void addCityWhereUserWorked(long userId, Set<String> cityWorked) throws SQLException {
        long cityId;
        for (String city : cityWorked) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, DBC.UCW_TABLE);
        }
    }
}
