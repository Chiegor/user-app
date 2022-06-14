package ru.app.userapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.PropertiesProvider;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void getAllUsers() throws SQLException {
        String query = "select * from User";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("userName"));
        }
    }

    public void getAllCities() throws SQLException {
        String query = "select * from City";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("cityName"));
        }
    }

    // задваивает значения
    public void getAllCityWhereUserLived(String userName) throws SQLException {
        String query = "select User.userName," +
                "City.cityName " +
                "from User, City, UCL " +
                "where User.userID = UCL.userID " +
                "and User.userName = '" + userName + "'"; // почитать про PreparedStatement
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("User.userName") + " " +
                    resultSet.getString("City.cityName"));
        }
    }

    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {
        String query = "select User.userName," +
                "City.cityName " +
                "from User, City, UCW " +
                "where User.userName = '" + userName + "'" +
                "and User.userID = UCW.userID"; // почитать про PreparedStatement
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("User.userName") + " " +
                    resultSet.getString("City.cityName"));
        }
    }
    @Override
    public Long createUser(User user) throws ApplicationException {
        try {

            String userName = user.getName();
            Set<String> cityLived = user.getCitiesLived();
            Set<String> cityWorked = user.getCitiesWorked();

//            String query = "INSERT INTO User (userName) Values (?)";
            String query = "INSERT INTO User (userName) Values ('" + userName + "')";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            log.debug("creating new user {}", user);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new ApplicationException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);

                    log.debug("created user id=" + id);
                    System.out.println("User id is: " + id);
                    return id;
                } else {
                    throw new ApplicationException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e){
            throw new ApplicationException("error creating user in db", e);
        }

    }

    @Override
    public void updateUserByName(String userName) throws SQLException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String request = reader.readLine();
            System.out.println("Press 'l' if you want to add city where user lived");
            System.out.println("Press 'w' if you want to add city where user worked");

            if (request.equals("l")){
                System.out.println("User name is " + userName);
                System.out.println("Enter city name: ");
                String city = reader.readLine();
                addCityWhereUserLived(userName, city);
            } else {
                System.out.println("User name is " + userName);
                System.out.println("Enter city name: ");
                String city = reader.readLine();
                addCityWhereUserWorked(userName, city);
            }
        }
    }

    @Override
    public void getUserByName(String userName) throws SQLException {

    }

    @Override
    public void getAllUserByCityLived(String cityName) throws SQLException {

    }

    @Override
    public void getAllUserByCityWorked(String cityName) throws SQLException {

    }

    @Override
    public void deleteUserByName(String userName) throws SQLException {

    }

    @Override
    public void addCityWhereUserLived(String userName, String cityName) throws SQLException {

    }

    @Override
    public void addCityWhereUserWorked(String userName, String cityName) throws SQLException {

    }

}
