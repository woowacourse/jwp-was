package net.slipp.presentation.dto;

import net.slipp.domain.User;

public class JoinRequest {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public JoinRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
