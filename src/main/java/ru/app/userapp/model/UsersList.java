package ru.app.userapp.model;

import ru.app.userapp.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersList implements Serializable {

    private List<User> users = new ArrayList<>();

    public List<User> getList (){
        return this.users;
    }

    public int size (){
        return users.size();
    }

    public void uAdd (User user){
        this.users.add(user);
    }

    public void uGet (int id){
        System.out.println(users.get(id).toString());
    }

    public User get (int id){
        return this.users.get(id);
    }

    public void uDelete (int id){
        this.users.remove(id);
    }

    public void uGetAll (){
        for (User user : this.users) {
            System.out.println("ID " + this.users.indexOf(user));
            System.out.println(user);
            System.out.println(" - - - - ");
        }
    }
}
