package dto;

import java.util.List;

import domain.model.User;
import lombok.Getter;

@Getter
public class UserListResponse extends ResponseDto<UserListResponse> {

    private final List<User> users;

    private UserListResponse(List<User> users) {
        this.users = users;
    }

    public static UserListResponse of(List<User> users) {
        return new UserListResponse(users);
    }
}
