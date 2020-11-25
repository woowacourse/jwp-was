package net.slipp.domain;

import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryFactory {

    private static final UserRepository USER_REPOSITORY;

    static {
        USER_REPOSITORY = new MemoryUserRepository(new ConcurrentHashMap<>());
    }

    private UserRepositoryFactory() {
    }

    public static UserRepository getInstance() {
        return USER_REPOSITORY;
    }
}
