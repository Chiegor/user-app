package ru.app.userapp;

import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.service.UserServiceImpl;
import ru.app.userapp.ui.ConsoleUserInterface;

import java.io.IOException;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        ConsoleUserInterface session = new ConsoleUserInterface();
        session.start();
    }
}

