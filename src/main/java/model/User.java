package model;

import java.util.Objects;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(String userId, String password, String name, String email) {
        validate(userId, password, name, email);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validate(String userId, String password, String name, String email) {
        if (Objects.requireNonNull(userId).isEmpty()) {
            throw new IllegalArgumentException("userId는 필수값입니다.");
        }
        if (Objects.requireNonNull(password).isEmpty()) {
            throw new IllegalArgumentException("password는 필수값입니다.");
        }
        if (Objects.requireNonNull(name).isEmpty()) {
            throw new IllegalArgumentException("name은 필수값입니다.");
        }
        if (Objects.requireNonNull(email).isEmpty()) {
            throw new IllegalArgumentException("email은 필수값입니다.");
        }
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email="
            + email + "]";
    }
}
