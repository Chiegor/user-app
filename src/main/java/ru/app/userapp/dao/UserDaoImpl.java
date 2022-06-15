package ru.app.userapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.PropertiesProvider;
import ru.app.userapp.exception.ApplicationException;
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
        String query = "select * from User order by userId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("userId") + " " + resultSet.getString("userName"));
        }
    }

    // #2
    @Override
    public void getAllCities() throws SQLException {
        String query = "select * from City order by cityId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("cityId") + " " + resultSet.getString("cityName"));
        }
    }

    // #3
    @Override
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        String query = "select distinct City.cityName " +
                "from User " +
                "join UCL " +
                "on User.userId = UCL.userId " +
                "and User.userName = '" + userName + "' " +
                "join City " +
                "on City.cityId = UCL.cityId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("City.cityName"));
        }
    }
    // #4
    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {
        String query = "select distinct City.cityName " +
                "from User " +
                "join UCW " +
                "on User.userId = UCW.userId " +
                "and User.userName = '" + userName + "' " +
                "join City " +
                "on City.cityId = UCW.cityId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("City.cityName"));
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
            String query = "INSERT INTO User (userName) Values ('" + userName + "')";
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
                    putCityIdUserIdInTable(userId, cityId, "UCL");
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, "UCL");
                }
            }

            for (String city : cityWorked) {
                long cityId = getCityId(city);
                if (cityId != 0L) {
                    putCityIdUserIdInTable(userId, cityId, "UCW");
                } else {
                    long cityID = createCity(city);
                    putCityIdUserIdInTable(userId, cityID, "UCW");
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
            String query = "insert into City (cityName) values ('" + cityName + "')";
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
                    System.out.println("New city id is: " + cityId);
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
                    "userId, cityId) " +
                    "values (" + userId + ", " + cityId + ")";
            PreparedStatement statement = connection.prepareStatement(query);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {throw new ApplicationException("putCityIdUserIdInTable error");}
            statement.close();
            System.out.println("Update is successful");
        } catch (SQLException e) {
            throw  new ApplicationException("ERROR put City Id User Id In Table", e);
        }
    }

    @Override
    public Long getCityId(String cityName) throws SQLException {
        long id = 0L;
        String query = "select cityId from City where cityName = '" + cityName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            id = resultSet.getLong("City.cityId");
        }
        return id;
    }

    // #6
    @Override
    public Long getUserIdByName(String userName) throws SQLException {
        long id = 0L;
        String query = "select userId from User where userName = '" + userName + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            id = resultSet.getLong("User.userId");
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
        String query = "select distinct User.userName " +
                "from City " +
                "join UCL " +
                "on City.cityId = UCL.cityId " +
                "and City.cityName = '" + cityName + "' " +
                "join User " +
                "on User.userId = UCL.userId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("User.userName"));
        }
    }

    @Override
    public void getAllUserByCityWorked(String cityName) throws SQLException {
        String query = "select distinct User.userName " +
                "from City " +
                "join UCW " +
                "on City.cityId = UCW.cityId " +
                "and City.cityName = '" + cityName + "' " +
                "join User " +
                "on User.userId = UCW.userId";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("User.userName"));
        }
    }

    @Override
    public void deleteUser(long id) throws SQLException {
        String query = "delete from User where User.userId = " + id + "";
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
        String tableName = "UCL";
        long cityId;
        for (String city : cityLived) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, tableName);
        }
    }

    @Override
    public void addCityWhereUserWorked(long userId, Set<String> cityWorked) throws SQLException {
        String tableName = "UCW";
        long cityId;
        for (String city : cityWorked) {
            cityId = getCityId(city);
            if (cityId == 0L) {
                cityId = createCity(city);
            }
            putCityIdUserIdInTable(userId, cityId, tableName);
        }
    }
}
