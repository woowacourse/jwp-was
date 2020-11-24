package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class UserTest {
    @DisplayName("저장된 user의 패스워드가 같은지 확인")
    @Test
    void isSamePasswordTest() {
        User user = new User("lavine", "lavine", "lavine", "lavine@gmail.com");

        assertAll(
                () -> assertThat(user.isSamePassword("lavine")).isTrue(),
                () -> assertThat(user.isSamePassword("lavine2")).isFalse()
        );
    }
}
