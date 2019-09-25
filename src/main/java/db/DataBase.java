package db;

import com.google.common.collect.Maps;
import db.exception.NotFoundException;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static User findUserByIdAndPassword(String userId, String password) {
        User user = users.get(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        return user.getCheckUserIdAndPassword(userId, password);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
