package ru.app.userapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLWorker {

    private final String HOST = "jdbc:postgresql://localhost:5432/java_ee_db";
    private final String USERNAME = "egorcicerin";
    private final String PASSWORD = "";

    private Connection connection;

    public void getPostgreSqlDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from books");

            while (resultSet.next()) {
                System.out.print(resultSet.getString("title") + " / ");
                System.out.print(resultSet.getString("author") + " / ");
                System.out.print(resultSet.getString("quantity"));
                System.out.println("\n");
            }
            statement.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
