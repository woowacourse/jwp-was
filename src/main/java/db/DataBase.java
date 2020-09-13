package db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import model.User;

public class DataBase {

    private Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
