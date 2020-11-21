package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findByUserId(String userId) {
        if (users.get(userId) == null) {
            return Optional.empty();
        }
        return Optional.of(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void deleteAll() {
        users.clear();
    }
}
