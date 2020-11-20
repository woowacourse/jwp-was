package dto;

import domain.model.User;
import java.util.List;

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
