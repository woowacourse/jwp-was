package service;

import db.DataBase;
import exception.NotExistUserException;
import java.util.Map;
import java.util.Objects;
import model.domain.User;
import model.request.HttpRequest;

public class UserService {

    public static UserService getInstance() {
        return LazyHolder.userService;
    }

    private static class LazyHolder {

        private static final UserService userService = new UserService();
    }

    public void createUser(HttpRequest httpRequest) {
        User user = User.of(httpRequest.extractParameters());

        DataBase.addUser(user);
    }

    public User login(HttpRequest httpRequest) throws NotExistUserException {
        Map<String, String> parameters = httpRequest.extractParameters();
        String requestId = parameters.get("userId");
        String requestPassword = parameters.get("password");

        User user = DataBase.findUserById(requestId);

        if (Objects.isNull(user) || !user.checkPassword(requestPassword)) {
            throw new NotExistUserException("Not Exist User");
        }

        return user;
    }
}
