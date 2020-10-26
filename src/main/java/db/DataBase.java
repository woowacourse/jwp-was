package db;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import web.application.domain.model.User;

public class DataBase {

    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        User copiedUser = user.copy();
        users.put(copiedUser.getUserId(), copiedUser);
    }

    public static Optional<User> findUserById(String userId) {
        User user = users.get(userId);
        try {
            return Optional.ofNullable(user.copy());
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public static List<User> findAll() {
        return users.values()
            .stream()
            .map(User::copy)
            .collect(Collectors.toList());
    }

    public static void deleteUser(String userId) {
        users.remove(userId);
    }
}
