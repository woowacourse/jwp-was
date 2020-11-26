package application.dto;

import application.model.User;

public class UserResponse {

    private String userId;
    private String name;
    private String email;

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
