package net.slipp.domain;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    void addUser(User user);

    boolean isExistUserId(String email);

    Optional<User> findUserById(String userId);

    Collection<User> findAll();
}
