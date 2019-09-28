package db;

import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Database {
    private static Map<String, User> users = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static Optional<User> findUserById(String id) {
        return Optional.of(users.get(id));
    }

    public static Optional<User> findUserByIdAndPassword(String id, String password) {
        Optional<User> maybeUser = findUserById(id);
        if (maybeUser.isPresent()) {
            return maybeUser.filter(user -> user.isMatchPassword(password));
        }

        return Optional.empty();
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}