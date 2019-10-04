package domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void checkPassword() {
        User user = new User("COogie", "coogie", "김효건", "coogie@naver.com");
        assertThat(user.notMatchPassword("cookie")).isTrue();
    }
}