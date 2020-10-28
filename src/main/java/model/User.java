package model;

import java.util.Map;
import java.util.Objects;

import web.RequestBody;

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

    public static User from(RequestBody requestBody) {
        Map<String, String> params = requestBody.getParams();
        return new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
    }

    public boolean login(String password) {
        return Objects.equals(this.password, password);
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
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
