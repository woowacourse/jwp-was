package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("유저 password가 input password와 같으면 true를 반환한다.")
    void checkPassword_ifInpuPassword_isSameAsUserPassword() {
        User user = new User("test", "test password", "test", "test@test.com");

        assertThat(user.hasSamePasswordWith("test password")).isTrue();

    }
}