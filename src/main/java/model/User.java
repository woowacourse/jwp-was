package model;

import java.util.Objects;

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

    public User(final String userId, final String password) {
        this.userId = userId;
        this.password = password;
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
        return "User {" +
                "userId: \"" + userId + "\"" +
                ", password: \"" + password + "\"" +
                ", name: \"" + name + "\"" +
                ", email: \"" + email + "\"" +
                "}";
    }

    @Override
    public boolean equals(final Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        final User user = (User) another;
        return Objects.equals(this.userId, user.userId) &&
                Objects.equals(this.password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password);
    }
}
