package jwp.was.db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import jwp.was.configure.annotation.Repository;
import jwp.was.model.User;

@Repository
public class DataBase {

    private static final Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }

    void clear() {
        users.clear();
    }
}
