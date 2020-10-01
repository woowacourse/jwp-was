package model;

import utils.StringUtils;

public class User {
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

    public static User of(String parameters) {
        String userId = StringUtils.extractParameterValue(parameters, "userId");
        String password = StringUtils.extractParameterValue(parameters, "password");
        String name = StringUtils.extractParameterValue(parameters, "name");
        String email = StringUtils.extractParameterValue(parameters, "email");

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
