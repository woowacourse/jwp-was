package db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import model.User;

public class DataBase {

    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return Optional.ofNullable(users.get(userId))
            .orElseThrow(() -> new IllegalArgumentException("id : " + userId + "를 찾을 수 없습니다!"));
    }

    public static Collection<User> findAll() {
        return Collections.unmodifiableCollection(users.values());
    }
}
