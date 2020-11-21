package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    // TODO: 2020/11/22 클래스명 변경
    private static Map<String, User> users = Maps.newHashMap();
    // TODO: 2020/11/22 Deprecated api 변경

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
