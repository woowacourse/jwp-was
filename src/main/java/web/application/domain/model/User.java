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

    public boolean hasPasswordSameWith(String password) {
        return this.password.equals(password);
    }
}
