package ru.app.userapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {

    private final String HOST = "jdbc:mysql://localhost:3306/users";
    private final String USERNAME = "root";
    private final String PASSWORD = "Fancytrumpet132";

    private Connection connection;

    public DBWorker () {
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            if (!connection.isClosed()){
                System.out.println("Connection successful");
            }
        } catch (SQLException sqlException){
            System.out.println("SQL connection error");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
