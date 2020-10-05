package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private final static String USER_PARAMETERS
        = "?userId=javajigi&password=password&name=자바지기&email=javajigi@gmail.com";

    @Test
    @DisplayName("User of")
    void create() {
        User user = User
            .of(USER_PARAMETERS);

        assertThat(user).isInstanceOf(User.class);
    }

    @Test
    void getUserId() {
        User user = User
            .of(USER_PARAMETERS);

        assertThat(user.getUserId()).isEqualTo("javajigi");
    }

    @Test
    void getName() {
        User user = User
            .of(USER_PARAMETERS);

        assertThat(user.getName()).isEqualTo("자바지기");
    }
}
