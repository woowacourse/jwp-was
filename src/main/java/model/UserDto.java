package model;

import http.model.request.HttpParameters;

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

    public UserDto(HttpParameters parameters) {
        this(parameters.getParameter("userId"), parameters.getParameter("password"),
                parameters.getParameter("name"), parameters.getParameter("email"));
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }


}
