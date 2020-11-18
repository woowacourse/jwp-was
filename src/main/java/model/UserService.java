package model;

import db.DataBase;

import java.util.Map;

public class UserService {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public static void create(Map<String, String> parameters) {
        User user = new User(parameters.get(USER_ID), parameters.get(PASSWORD),
                parameters.get(NAME), parameters.get(EMAIL));
        DataBase.addUser(user);
    }
}
