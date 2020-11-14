package net.slipp.presentation.dto;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.List;

import net.slipp.domain.User;

public class UsersResponse {
    private final List<UserResponse> users;

    private UsersResponse(List<UserResponse> users) {
        this.users = users;
    }

    public static UsersResponse ofList(Collection<User> users) {
        return users.stream()
                .map(UserResponse::of)
                .collect(collectingAndThen(toList(), UsersResponse::new));
    }

    public List<UserResponse> getUsers() {
        return users;
    }

    private static class UserResponse {
        private final String userId;
        private final String name;
        private final String email;

        public UserResponse(String userId, String name, String email) {
            this.userId = userId;
            this.name = name;
            this.email = email;
        }

        public static UserResponse of(User user) {
            return new UserResponse(user.getUserId(), user.getName(), user.getEmail());
        }

        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
