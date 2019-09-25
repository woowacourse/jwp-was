package db;

import com.google.common.collect.Maps;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DataBase {
    private static final Logger log = LoggerFactory.getLogger(DataBase.class);
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        log.debug("Begin {}", user);
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return Optional.ofNullable(users.get(userId)).orElseThrow(NotFoundEntityException::new);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
