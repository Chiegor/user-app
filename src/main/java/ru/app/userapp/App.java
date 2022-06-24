package ru.app.userapp;

import ru.app.userapp.ui.ConsoleUserInterface;

public class App {
    public static void main(String[] args) {
        ConsoleUserInterface session = new ConsoleUserInterface();
        session.start();
    }
}

