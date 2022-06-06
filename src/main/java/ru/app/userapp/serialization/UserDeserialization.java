package ru.app.userapp.serialization;

import ru.app.userapp.model.UsersList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class UserDeserialization implements Serializable {
    public UsersList load () {
        String fileName = "/Users/egorcicerin/code/user-app/src/main/resources/users.bin";
        UsersList loadUserList = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            loadUserList = (UsersList) ois.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return loadUserList;
    }
}

