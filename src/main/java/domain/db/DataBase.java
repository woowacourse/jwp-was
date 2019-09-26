package domain.db;

import com.google.common.collect.Maps;
import domain.model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("qwe", new User("qwe", "qwe", "qwe", "qwe@naver.com"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
