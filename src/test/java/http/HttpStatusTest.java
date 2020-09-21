package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HttpStatusTest {
    @ParameterizedTest
    @MethodSource(value = {"provideValidHttpStatus"})
    @DisplayName("HttpStatus 확인 테스트")
    void from(int statusCode, HttpStatus expected) {
        assertThat(HttpStatus.from(statusCode)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideValidHttpStatus() {
        return Stream.of(
            Arguments.of(200, HttpStatus.OK),
            Arguments.of(304, HttpStatus.FOUND),
            Arguments.of(404, HttpStatus.NOT_FOUND)
        );
    }
}
