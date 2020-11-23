package net.slipp.application;

import net.slipp.domain.UserRepositoryFactory;

public class UserServiceFactory {

    private static final UserService USER_SERVICE;

    static {
        USER_SERVICE = new UserService(UserRepositoryFactory.getInstance());
    }

    private UserServiceFactory() {
    }

    public static UserService getInstance() {
        return USER_SERVICE;
    }
}
