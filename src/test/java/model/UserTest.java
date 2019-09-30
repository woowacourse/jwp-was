package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    public static final String ID = "id";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email@email.email";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(ID, PASSWORD, NAME, EMAIL);
    }

    @Test
    void 로그인() {
        assertThat(user.matchPassword(PASSWORD)).isTrue();
    }

    @Test
    void 다른_비밀번호로_로그인시_에러() {
        assertThat(user.matchPassword("abc")).isFalse();
    }
}
