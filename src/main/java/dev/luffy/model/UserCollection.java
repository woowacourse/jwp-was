package dev.luffy.model;

import java.util.Collection;

public class UserCollection {
    private Collection<User> users;

    public UserCollection(Collection<User> users) {
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
