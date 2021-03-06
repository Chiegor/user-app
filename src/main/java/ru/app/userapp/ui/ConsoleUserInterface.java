package ru.app.userapp.ui;

import ru.app.userapp.controller.UserControllerImpl;
import ru.app.userapp.exception.ApplicationException;
import ru.app.userapp.service.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ConsoleUserInterface {
    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Hello! Press 'h' to get list of command");
            String firstCommand = "";
            UserControllerImpl request = new UserControllerImpl(new UserServiceImpl());
            String userName;
            String cityName;
            long userId;
            while (!(firstCommand = reader.readLine()).equals("")) {
                switch (firstCommand) {
                    case "1":
                        System.out.println("Get all user from DB");
                        request.getAllUsers();
                        break;
                    case "2":
                        System.out.println("Get all city from DB");
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
                        System.out.println("Start create new user");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();

                        System.out.println("Enter city name where user lived: ");
                        System.out.println("enter 'done' when finish");
                        Set<String> cityLivedSet;
                        cityLivedSet = addCityToSet();

                        System.out.println("Enter city name where user worked: ");
                        System.out.println("enter 'done' when finish");
                        Set<String> cityWorkedSet;
                        cityWorkedSet = addCityToSet();
                        userId = request.createUser(userName, cityLivedSet, cityWorkedSet);
                        System.out.println(userName + " id is: " + userId);
                        break;
                    case "6":
                        System.out.println("Get user id by user name");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();
                        userId = request.getUserIdByName(userName);
                        System.out.println(userName + " id: " + userId);
                        break;
                    case "7":
                        System.out.println("Update user info");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();
                        request.updateByUserName(userName);
                        break;
                    case "8":
                        System.out.println("Delete user by user name");
                        System.out.println("Enter user name: ");
                        userName = reader.readLine();
                        request.deleteUser(userName);
                        break;

                    case "9":
                        System.out.println("Get all user by city lived");
                        System.out.println("Enter city: ");
                        cityName = reader.readLine();
                        request.getAllUserByCityLived(cityName);
                        break;

                    case "10":
                        System.out.println("Get all user by city worked");
                        System.out.println("Enter city: ");
                        cityName = reader.readLine();
                        request.getAllUserByCityWorked(cityName);
                        break;

                    case "h":
                        System.out.println("1 - get all user from user's list");
                        System.out.println("2 - get all city from city's list");
                        System.out.println("3 - get all city where user lived");
                        System.out.println("4 - get all city where user worked");
                        System.out.println("5 - create new user and add it to the users list. Return user's ID");
                        System.out.println("6 - Get user id by user name");
                        System.out.println("7 - update - update user by user name or user id");
                        System.out.println("8 - delete - delete user by user name or user id");
                        System.out.println("9 - get all user by city lived");
                        System.out.println("10 - get all user by city worked");
                        System.out.println("h - help - get list of command");
                    default:
                        System.out.println("Unknown command" + "\n");
                        System.out.println("Press 'h' to get a list of commands");
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
        } catch (IOException e) {
            throw new ApplicationException("error addCityToSet", e);
        }
    }
}
