package utils;

import model.User;

import java.util.Collection;

public class UsersDto {
    private final Collection<User> users;

    public UsersDto(Collection<User> users) {
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
