package model;

import java.util.Map;

public class UserDto {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    private UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserDto from(Map<String, String> body) {
        return new UserDto(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
