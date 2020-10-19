package model;

import java.util.Map;

import http.request.Parameters;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User from(Map<String, String> parameters) {
        return new User(parameters.get("userId"), parameters.get("password"),
                parameters.get("name"), parameters.get("email"));
    }

    public static User from(Parameters parameters) {
        return new User(parameters.getParameterBy("userId"), parameters.getParameterBy("password"),
                parameters.getParameterBy("name"), parameters.getParameterBy("email"));
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email="
                + email + "]";
    }
}
