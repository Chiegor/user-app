package ru.app.userapp;

import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.ui.ConsoleUserInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        ConsoleUserInterface session = new ConsoleUserInterface();
        session.start();
    }
}

