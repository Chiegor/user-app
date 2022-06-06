package ru.app.userapp.dao;

import ru.app.userapp.model.Text;
import ru.app.userapp.model.User;
import ru.app.userapp.model.UsersList;
import ru.app.userapp.serialization.Selection;
import ru.app.userapp.serialization.UserDeserialization;
import ru.app.userapp.serialization.UserSerialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Dao {
    UsersList list = null;
    UserSerialization us = new UserSerialization();

    public void start (){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Do you want to download User List? y / n ");

            list = getUserList(reader.readLine());

            String firstCommand;
            while (!(firstCommand = reader.readLine()).equals("exit")) {

                switch (firstCommand) {
                    case "1":
                        System.out.println("Enter user name: ");
                        String userName = reader.readLine();

                        System.out.println("Enter city where user lived: ");
                        String cityWhereUserLivedFirst = reader.readLine();

                        list.uAdd(create(userName, cityWhereUserLivedFirst));
                        list.uGet(list.size() - 1);
                        System.out.println("User ID is " + (list.size() - 1));
                        us.save(list);
                        break;

                    case "2":
                        System.out.println(Text.enterID);
                        try {
                            list.uGet(Integer.parseInt(reader.readLine()));
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Такого индекса не существует");
                        }
                        break;

                    case "3":
                        list.uGetAll();
                        break;

                    case "4":
                        System.out.println(Text.enterID);
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println("You selected user ID - " + id);
                        list.uGet(id);
                        System.out.println();
                        System.out.println("Enter L if you want to add city where user lived or W to add where worked ");
                        String answer2 = reader.readLine();
                        if (answer2.equals("L")){
                            System.out.println("Enter city's names: ");
                            String city;
                            while (!(city = reader.readLine()).equals("")){
                                System.out.println("");
                                list.get(id).addCityLived(city);
                                System.out.println("");
                            }
                            System.out.println("done");
                        } else if (answer2.equals("W")){
                            System.out.println("Enter city's names: ");
                            String city;
                            while (!(city = reader.readLine()).equals("")) {
                                System.out.println("");
                                list.get(id).addCityWorked(city);
                                System.out.println("");
                            }
                            System.out.println("done");
                        }
                        System.out.println("User " + list.get(id));
                        us.save(list);
                        break;

                    case "5":
                        System.out.println(Text.enterID);
                        int delete = Integer.parseInt(reader.readLine());
                        list.uDelete(delete);
                        us.save(list);
                        System.out.println("User ID " + delete + " delete!");
                        System.out.println();
                        System.out.println("Now User list is " + list.size() + " size");
                        break;

                    case "6":
                        System.out.println("Sort by lived - L, sort by worked - any key: ");
                        String answer = reader.readLine();
                        if (answer.equals("L")){
                            System.out.println("Enter city name: ");
                            answer = reader.readLine();
                            Selection selection = new Selection();
                            List<String> listString = selection.ulc(list.getList(), answer);
                            for (String name : listString) {
                                System.out.print(name + ", ");
                            }
                            break;
                        } else {
                            System.out.println("Enter city name: ");
                            answer = reader.readLine();
                            Selection selection = new Selection();
                            List<String> listString = selection.uwc(list.getList(), answer);
                            for (String name : listString) {
                                System.out.print(name + ", ");
                            }
                            break;
                        }

                    case "7":
                        System.out.println("User list size is " + list.size());
                        break;
                    case "8":
                        us.save(list);
                        System.out.println("User list save!");
                        break;

                    case "9":
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


                    default:
                        System.out.println("Unknown command");
                        System.out.println();
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // метод загружает UsersList list из файла
    public UsersList download (){
        System.out.println("Downloading list of user... ");
        UserDeserialization ud = new UserDeserialization();
        System.out.println("Downloading is complete");
        return ud.load();
    }

    private User create (String name, String city){
        User user = new User(name, city);
        return user;
    }

    // метод позволяет при запуске программы выбрать будет ли UsersList list создан заного или будет загружен из файла
    public UsersList getUserList (String answer){
        if (answer.equals("y")){
            System.out.println("OK, user list is download ");
            return download();
        } else {
            System.out.println("OK, new User list is created ");
            return new UsersList();
        }
    }
}
