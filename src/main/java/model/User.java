package model;

import java.util.Map;

public class User {

    private static final String KEY_VALUE_LINK_LETTER = "=";
    private static final String PARAMETER_DELIMITER = "&";
    private static final int VALUE_RIGHT_SIDE_INDEX = 1;
    private static final int VALUE_INDEX = 0;

    private String userId;
    private String password;
    private String name;
    private String email;

    private User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        return new User(userId, password, name, email);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
