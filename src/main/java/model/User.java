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

    public String id() {
        return this.id;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User rhs = (User) o;
        return this.id.equals(rhs.id) &&
                this.password.equals(rhs.password) &&
                this.name.equals(rhs.name) &&
                this.email.equals(rhs.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.password, this.name, this.email);
    }
}
