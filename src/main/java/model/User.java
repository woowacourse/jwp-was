package model;

import db.exception.WrongPasswordException;

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

    public User getCheckUserIdAndPassword(String userId, String password) {
        if (!sameUserIdAndPassword(userId, password)) {
            throw new WrongPasswordException();
        }
        return this;
    }

    public boolean sameUserIdAndPassword(String userId, String password) {
        return this.userId.equals(userId) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
