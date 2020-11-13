package net.slipp.domain;

import java.util.Collection;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> users;

    public MemoryUserRepository(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public boolean isExistUserId(String userId) {
        return users.containsKey(userId);
    }

    @Override
    public User findUserById(String userId) {
        return users.get(userId);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }
}
