package service;

import db.DataBase;
import java.util.Map;
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

    public User login(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.extractParameters();
        String requestId = parameters.get("userId");
        String requestPassword = parameters.get("password");

        User user = DataBase.findUserById(requestId);

        if(Objects.isNull(user) || !user.checkPassword(requestPassword)){
            throw new IllegalArgumentException();
        }

        return user;
    }
}
