package ru.app.userapp;

import ru.app.userapp.controller.Controller;
import ru.app.userapp.dao.UserDaoImpl;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        Controller session = new Controller();
        session.start();
    }
}

