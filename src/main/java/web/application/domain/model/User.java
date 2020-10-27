package web.application.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder
public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    @Override
    public User clone() {
        return User.builder()
            .userId(getUserId())
            .password(getPassword())
            .name(getName())
            .email(getEmail())
            .build();
    }

    public boolean hasPasswordSameWith(String password) {
        return this.password.equals(password);
    }
}
