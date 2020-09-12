package controller;

import db.DataBase;
import dto.UserCreateRequest;
import model.User;

public class UserController {

    public void createUser(UserCreateRequest request) { // Todo: 응답을 return 하도록 수정
        User user = new User(request.getUserId(),
            request.getPassword(),
            request.getName(),
            request.getEmail()
        );
        DataBase.addUser(user);
    }
}
