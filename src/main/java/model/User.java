package model;

import webserver.http.HttpRequest;

import java.util.Objects;

public class User {
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    private String id;
    private String password;
    private String name;
    private String email;

    public static User of(HttpRequest request) {
        return User.of(
                request.getParam(USER_ID),
                request.getParam(USER_PASSWORD),
                request.getParam(USER_NAME),
                request.getParam(USER_EMAIL)
        );
    }

    public static User of(String id, String password, String name, String email) {
        return new User(id, password, name, email);
    }

    private User(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
    }

    public String getId() {
        return this.id;
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
        return "User{" +
                "id='" + this.id + '\'' +
                ", password='" + this.password + '\'' +
                ", name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, email);
    }
}
