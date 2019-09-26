package model;

import java.util.Objects;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    private User(String userId, String password, String name, String email) {
        this.userId = validate(userId);
        this.password = validate(password);
        this.name = validate(name);
        this.email = validate(email);
    }

    public static Builder builder() {
        return new Builder();
    }

    private String validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalUserArgsException();
        }
        return value;
    }

    public String getUserId() {
        return userId;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public static class Builder {
        private String userId;
        private String password;
        private String name;
        private String email;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(userId, password, name, email);
        }
    }
}
