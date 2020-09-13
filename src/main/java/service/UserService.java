package service;

import db.DataBase;
import model.User;
import service.dto.UserRequest;

public class UserService {

    private final DataBase dataBase;

    public UserService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public User createUser(UserRequest userRequest) {
        User user = userRequest.toUser();
        dataBase.addUser(user);
        return user;
    }
}
