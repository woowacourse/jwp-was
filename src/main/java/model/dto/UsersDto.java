package model.dto;

import model.domain.User;

import java.util.List;

public class UsersDto {
    private final List<User> users;

    public UsersDto(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
