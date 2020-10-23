package web.application.dto;

import java.util.List;
import web.application.domain.model.User;

public class UserListResponse extends ResponseDto {

    private List<User> users;

    private UserListResponse(List<User> users) {
        this.users = users;
    }

    public static UserListResponse of(List<User> users) {
        return new UserListResponse(users);
    }

    public List<User> getUsers() {
        return users;
    }
}
