package model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @DisplayName("인증 실패")
    @Test
    public void notAuthenticated() {
        User user = new User("javajigi", "123123", "pobi", "test@test.com");

        boolean result = user.notAuthenticated("321321");

        assertThat(result).isTrue();
    }
}
