package model;

import java.util.Map;

public class UserAssembler {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public static User toEntity(Map<String, String> userInfo) {
        return new User(userInfo.get(USER_ID),
                userInfo.get(PASSWORD),
                userInfo.get(NAME),
                userInfo.get(EMAIL));
    }
}
