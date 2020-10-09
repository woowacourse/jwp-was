package model;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest {

    private static Stream<Arguments> provideIsSamePassword() {
        return Stream.of(
                Arguments.of("password", true),
                Arguments.of("notpassword", false)
        );
    }

    @DisplayName("isSamePassword")
    @MethodSource("provideIsSamePassword")
    @ParameterizedTest
    void isSamePassword(final String password, final boolean expect) {
        // given
        User user = new User("userId", "password", "name", "email@email.com");

        // when
        // then
        assertThat(user.isSamePassword(password)).isEqualTo(expect);
    }
}
