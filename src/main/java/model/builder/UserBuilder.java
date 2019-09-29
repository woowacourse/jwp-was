package model.builder;

import model.User;

public class UserBuilder {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.name = email;
        return this;
    }

    public User build() {
        return new User(userId, password, name, email);
    }
}