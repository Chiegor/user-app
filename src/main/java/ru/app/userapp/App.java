package ru.app.userapp;

import ru.app.userapp.ui.ConsoleUserInterface;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        ConsoleUserInterface session = new ConsoleUserInterface();
        session.start();
    }
}

