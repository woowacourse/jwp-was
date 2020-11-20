package service;

import db.DataBase;
import domain.model.User;
import java.util.Optional;

public class UserService {

    public static UserService getInstance() {
        return Cache.USER_SERVICE;
    }

    public boolean isValidatedUser(String userId, String password) {
        Optional<User> userById = DataBase.findUserById(userId);
        return userById.map(user -> user.hasPasswordSameWith(password))
            .orElse(false);
    }

    private static class Cache {

        private final static UserService USER_SERVICE = new UserService();
    }
}
