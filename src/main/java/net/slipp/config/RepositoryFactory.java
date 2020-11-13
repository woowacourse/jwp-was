package net.slipp.config;

import java.util.concurrent.ConcurrentHashMap;

import net.slipp.domain.MemoryUserRepository;
import net.slipp.domain.UserRepository;

public class RepositoryFactory {
    private static final UserRepository USER_REPOSITORY;

    static {
        USER_REPOSITORY = new MemoryUserRepository(new ConcurrentHashMap<>());
    }

    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }
}
