package net.slipp.config;

import net.slipp.application.UserService;

public class ServiceFactory {
    private static final UserService USER_SERVICE;

    static {
        USER_SERVICE = new UserService(RepositoryFactory.getUserRepository());
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }
}
