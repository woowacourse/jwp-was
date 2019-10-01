package db;

import com.google.common.collect.Maps;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static final Logger log = LoggerFactory.getLogger(DataBase.class);

    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("test1", new User("test1", "1234", "name", "test1@gmail.com"));
        users.put("test2", new User("test2", "1234", "name", "test2@gmail.com"));
        users.put("test3", new User("test3", "1234", "name", "test3@gmail.com"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
        log.debug("db : {}", users.get(user.getUserId()).toString());
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
