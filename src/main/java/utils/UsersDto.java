package utils;

import java.util.Collection;

import model.User;

public class UsersDto {
    private final Collection<User> users;

    public UsersDto(Collection<User> users) {
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
