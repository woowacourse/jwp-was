package application.db;

import application.controller.WrongUserIdPasswordException;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import model.User;

public class DataBase {

    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) throws WrongUserIdPasswordException {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        throw new WrongUserIdPasswordException("user that id is " + userId + " is not exist.");
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
