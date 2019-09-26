package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static{
        User user = new User("asd", "asd", "asd", "asd@asd");
        users.put(user.getUserId(), user);
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
