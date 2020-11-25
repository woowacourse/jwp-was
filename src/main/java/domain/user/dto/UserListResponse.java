package domain.user.dto;

import domain.user.User;

import java.util.Collection;

public class UserListResponse {
    private Collection<User> users;

    public UserListResponse(Collection<User> users) {
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
