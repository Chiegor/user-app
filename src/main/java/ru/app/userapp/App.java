package ru.app.userapp;

import ru.app.userapp.dao.Dao;
import ru.app.userapp.model.Text;
import ru.app.userapp.sql.DBWorker;

import java.sql.*;

public class App {

    public static void main(String[] args) throws SQLException {
        DBWorker worker = new DBWorker();

        String query = "select * from user";

        Statement statement = worker.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

//        while (resultSet.next()){
//            User user = new User();
//            user.setId(resultSet.getInt("id"));
//            user.setUsername(resultSet.getString("name"));
//            user.setPassword(resultSet.getString("password"));
//            System.out.println(user);

        System.out.println(Text.welcomeMessege);
        System.out.println("");

        Dao session = new Dao();
        session.start();





        }
    }
