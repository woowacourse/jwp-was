package model.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private static Map<String, String> userParameters;

    @BeforeEach
    void setUp() {
        userParameters = new HashMap<>();

        userParameters.put("userId", "javajigi");
        userParameters.put("password", "password");
        userParameters.put("name", "자바지기");
        userParameters.put("email", "javajigi@gmail.com");
    }

    @Test
    @DisplayName("User 생성")
    void create() {
        User user = User
            .of(userParameters);

        assertThat(user).isInstanceOf(User.class);
    }

    @Test
    void getUserId() {
        User user = User
            .of(userParameters);

        assertThat(user.getUserId()).isEqualTo("javajigi");
    }

    @Test
    void getName() {
        User user = User
            .of(userParameters);

        assertThat(user.getName()).isEqualTo("자바지기");
    }
}
