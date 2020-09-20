package domain.user.dto;

import domain.user.User;

public class UserRequest {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private String userId;
    private String password;
    private String name;
    private String email;

    public void set(String fieldName, String value) {
        if (USER_ID.equals(fieldName)) {
            this.userId = value;
        }
        if (PASSWORD.equals(fieldName)) {
            this.password = value;
        }
        if (NAME.equals(fieldName)) {
            this.name = value;
        }
        if (EMAIL.equals(fieldName)) {
            this.email = value;
        }
    }

    public User toUser() {
        return new User(this.userId, this.password, this.name, this.email);
    }
}
