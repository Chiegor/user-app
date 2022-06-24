package ru.app.userapp.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.app.userapp.PropertiesProvider;
import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.exception.ApplicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final Connection connection;

    public SQLConnection() {
        try {
            String url = PropertiesProvider.getProperties().getProperty("datasource.url");
            String username = PropertiesProvider.getProperties().getProperty("datasource.username");
            String password = PropertiesProvider.getProperties().getProperty("datasource.password");

            connection = DriverManager.getConnection(url, username, password);
            log.info("db connection created");
        } catch (SQLException e) {
            throw new ApplicationException("Connection to DB failed");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
