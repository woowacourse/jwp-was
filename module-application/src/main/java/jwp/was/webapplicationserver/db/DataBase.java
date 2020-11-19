package jwp.was.webapplicationserver.db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import jwp.was.webapplicationserver.configure.annotation.Repository;
import jwp.was.webapplicationserver.model.User;

@Repository
public class DataBase {

    private final Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public void clear() {
        users.clear();
    }
}
