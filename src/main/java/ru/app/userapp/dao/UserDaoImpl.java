package ru.app.userapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.PropertiesProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public void getUserByName(String userName) throws SQLException {

    }

    @Override
    public void getAllUserFromCityLived(String cityName) throws SQLException {

    }

    public void getAllCityWhereUserLived(String name) throws SQLException {
        String query = "select User.userName," +
                "City.cityName " +
                "from User, City, UCL " +
                "where User.userID = UCL.userID " +
                "and User.userName = '" + name + "'"; // почитать
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("User.userName") + " " +
                    resultSet.getString("City.cityName"));
        }

    }

    @Override
    public void getAllUserFromCityWorked(String cityName) throws SQLException {

    }

    @Override
    public void getAllCityWhereUserWorked(String userName) throws SQLException {

    }

    @Override
    public void createUser(String userName, String cityLived) throws SQLException {

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
    public void deleteUserByName(String userName) throws SQLException {

    }

    @Override
    public void addCityWhereUserLived(String userName, String cityName) throws SQLException {

    }

    @Override
    public void addCityWhereUserWorked(String userName, String cityName) throws SQLException {

    }

}
