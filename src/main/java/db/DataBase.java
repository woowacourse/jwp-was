package db;

import com.google.common.collect.Maps;
import db.exception.UserNotFoundException;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        throw new UserNotFoundException();
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static boolean contains(String userId) {
        return users.containsKey(userId);
    }

    public static boolean match(String userId, String password) {
        User user = findUserById(userId);
        return user.matchPassword(password);
    }
}
