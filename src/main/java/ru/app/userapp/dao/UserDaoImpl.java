package ru.app.userapp.dao;

import ru.app.userapp.model.User;
import ru.app.userapp.sql.MySQLWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request implements Dao {
    public static void getAllUsers () throws SQLException {
        MySQLWorker worker = new MySQLWorker();
        String query = "select * from User";
        Statement statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet.getString("userName"));
        }
    }

    public static void getAllCitys () throws SQLException {
        MySQLWorker worker = new MySQLWorker();
        String query = "select * from City";
        Statement statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet.getString("cityName"));
        }
    }

    public static void getUserCity (String name) throws SQLException {
        MySQLWorker worker = new MySQLWorker();
        String query = "select User.userName," +
                "City.cityName " +
                "from User, City, UC " +
                "where User.userID = UC.userID " +
                "and User.userName = '" + name + "'";
        Statement statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            System.out.println(resultSet.getString("User.userName") + " " +
                    resultSet.getString("City.cityName"));
        }

    }

    public static void createUser (String userName, String cityLived) throws SQLException{
        User user = new User(userName, cityLived);
        MySQLWorker worker = new MySQLWorker();
        String query = "insert into User(" +
                "userName) values ('" + userName + "')";
        Statement statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            System.out.println(resultSet);
        }
        int userID;
        String query2 = "select User.userID " +
                "from User " +
                "where User.userName = '" + userName + "'";
        resultSet = statement.executeQuery(query2);

    }
}
