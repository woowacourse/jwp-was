package mvc.db;

import com.google.common.collect.Maps;
import mvc.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataBase {
    private static Map<String, Optional<User>> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), Optional.of(user));
    }

    public static Optional<User> findUserById(String userId) {
        return users.getOrDefault(userId, Optional.empty());
    }

    public static Collection<User> findAll() {
        return users.values().stream().map(Optional::get).collect(Collectors.toList());
    }

    public static void deleteAll() {
        users.values().forEach(users::remove);
    }
}
