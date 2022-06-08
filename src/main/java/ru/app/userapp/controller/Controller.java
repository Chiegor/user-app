package ru.app.userapp.controller;

import ru.app.userapp.dao.UserDaoImpl;
import ru.app.userapp.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Controller {

    public void start() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Приветики. Нажми кнопку");
            String firstCommand;
            Service request = new Service();
            while (!(firstCommand = reader.readLine()).equals("exit")) {
                switch (firstCommand) {
                    case "1":
                        request.getAllUsers();
                        break;
                    case "2":
                        request.getAllCities();
                        break;
                    case "3":
                        System.out.println("Enter user name: ");
                        String userName = reader.readLine();
                        request.getAllCityWhereUserLived(userName);
                        break;
                    case "4":
                        System.out.println("Enter user name: ");
                        String name = reader.readLine();
                        System.out.println("Enter city name where user lived: ");
                        String cityName = reader.readLine();
                        request.createUser(name, cityName);
                        break;
                    default:
                        System.out.println("Unknown command" + "\n");
                        System.out.println("1 - get all user from user's list");
                        System.out.println("2 - get all city from city's list");
                        System.out.println("1 - create - create new user and add it to the users list");
                        System.out.println("2 - get - find user by its ID");
                        System.out.println("3 - getall - show list of users");
                        System.out.println("4 - update - update user by its ID");
                        System.out.println("5 - delete - delete user by its ID");
                        System.out.println("6 - select - to sort user by city");
                        System.out.println("7 - size - get size of User list");
                        System.out.println("8 - save - save current User list to users.bin");
                        System.out.println("9 - help - get list of command");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
