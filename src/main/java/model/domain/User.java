package model.domain;

import java.util.Map;

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

    public static User of(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        return new User(userId, password, name, email);
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email="
            + email + "]";
    }
}
