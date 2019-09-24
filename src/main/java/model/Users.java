package model;

import java.util.List;

public class Users {
    List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
