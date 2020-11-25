package domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    @DisplayName("notMatchPassword 기능 테스트")
    @ParameterizedTest
    @CsvSource(value = {"password:true", "password22:false"}, delimiter = ':')
    void notMatchPassword(String password, boolean expected) {
        User user1 = new User("1", "password", "dundung", "d@gmail.com");
        User user2 = new User("2", password, "dundung", "d@gmail.com");

        assertThat(user1.notMatchPassword(user2)).isEqualTo(expected);
    }
}