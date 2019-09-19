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

    public String getUserId() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + this.userId + '\'' +
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
        return Objects.equals(this.userId, rhs.userId) &&
                Objects.equals(this.password, rhs.password) &&
                Objects.equals(this.name, rhs.name) &&
                Objects.equals(this.email, rhs.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.password, this.name, this.email);
    }
}
