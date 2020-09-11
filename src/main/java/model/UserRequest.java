package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserRequest {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String USER_REQUEST_REGEX = "userId=([^&=]+)&password=([^&=]+)&name=([^&=]+)&email=([^&=]+)";

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserRequest() {
    }

    public List<String> getFieldNames() {
        return Arrays.asList(USER_ID, PASSWORD, NAME, EMAIL);
    }

    public boolean isLastField(String fieldName) {
        return Objects.equals(fieldName, EMAIL);
    }

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

    public String getRegex() {
        return USER_REQUEST_REGEX;
    }
}
