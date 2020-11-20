package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("유저의 비밀번호가 같은지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"nope,false", "password,true"})
    void hasSamePassword(String inputPassword, boolean expected) {
        User user = new User("123", "password", "123", "123");

        boolean result = user.hasSamePassword(inputPassword);

        assertThat(result).isEqualTo(expected);
    }
}