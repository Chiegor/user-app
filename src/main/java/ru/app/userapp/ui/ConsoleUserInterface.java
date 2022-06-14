package ru.app.userapp.ui;

import ru.app.userapp.controller.UserControllerImpl;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.service.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConsoleUserInterface {
    public void start() throws SQLException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Приветики. Нажми кнопку");
            String firstCommand;
            UserControllerImpl request = new UserControllerImpl(new UserServiceImpl());
            String userName;
            while (!(firstCommand = reader.readLine()).equals("exit")) {
                switch (firstCommand) {
                    case "1":
                        request.getAllUsers();
                        break;
                    case "2":
                        request.getAllCities();
                        break;
                    case "3":
                        System.out.println("Get all city where user lived");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();
                        request.getAllCityWhereUserLived(userName);
                        break;
                    case "4":
                        System.out.println("Get all city where user worked");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();
                        request.getAllCityWhereUserWorked(userName);
                        break;
                    case "5":
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();

                        System.out.println("Enter city name where user lived: ");
                        Set<String> cityLivedSet;
                        cityLivedSet = addCityToSet();

                        System.out.println("Enter city name where user worked: ");
                        Set<String> cityWorkedSet;
                        cityWorkedSet = addCityToSet();

                        request.createUser(userName, cityLivedSet, cityWorkedSet);
                        break;
                    case "6":



                    default:
                        System.out.println("Unknown command" + "\n");
                        System.out.println("1 - get all user from user's list");
                        System.out.println("2 - get all city from city's list");
                        System.out.println("3 - get all city where user lived");
                        System.out.println("4 - get all city where user worked");
                        System.out.println("5 - create new user and add it to the users list. Return user's ID");
                        System.out.println("6 - add new city");
                        System.out.println("7 - update - update user by user name");
                        System.out.println("8 - delete - delete user by user name");
                        System.out.println("9 - help - get list of command");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> addCityToSet() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Set<String> set = new HashSet<>();
            String city;
            while (!(city = reader.readLine()).equals("done")) {
                set.add(city);
            }
            return set;
        } catch (IOException e){
            throw new ApplicationException("error addCityToSet", e);
        }
    }
}
