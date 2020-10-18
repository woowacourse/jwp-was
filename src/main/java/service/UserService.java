package service;

import db.DataBase;
import java.util.Objects;
import model.domain.User;
import model.request.HttpRequest;

public class UserService {

    private static UserService userService;

    public static UserService getInstance() {
        if (Objects.isNull(userService)) {
            userService = new UserService();
        }
        return userService;
    }

    public void createUser(HttpRequest httpRequest) {
        User user = User.of(httpRequest.extractParameters());

        DataBase.addUser(user);
    }
}
