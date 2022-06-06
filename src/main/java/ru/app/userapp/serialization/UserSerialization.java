package ru.app.userapp.serialization;

import ru.app.userapp.model.UsersList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class UserSerialization implements Serializable {

    public void save (UsersList list) {
        String fileName = "/Users/egorcicerin/code/user-app/src/main/resources/users.bin";
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

