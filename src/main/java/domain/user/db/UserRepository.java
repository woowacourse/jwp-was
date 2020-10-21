package domain.user.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import domain.user.model.User;

public class UserRepository {
    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findByUserId(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
