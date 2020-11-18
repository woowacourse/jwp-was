package model;

import java.util.Map;

public class UserFactory {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public static User create(Map<String, String> parameters) {
        return new User(parameters.get(USER_ID), parameters.get(PASSWORD), parameters.get(NAME), parameters.get(EMAIL));
    }
}
