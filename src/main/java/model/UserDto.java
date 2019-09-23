package model;

import java.util.Map;

public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserDto(Map<String, String> params) {
        this(params.get("userId"), params.get("password"),
                params.get("name"), params.get("email"));
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
