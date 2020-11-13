package net.slipp.domain;

import java.util.Collection;

public interface UserRepository {

    void addUser(User user);

    boolean isExistUserId(String email);

    User findUserById(String userId);

    Collection<User> findAll();
}
