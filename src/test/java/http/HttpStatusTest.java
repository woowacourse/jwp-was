package http;

import static org.assertj.core.api.Assertions.assertThat;

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
            Arguments.of(201, HttpStatus.CREATED),
            Arguments.of(302, HttpStatus.FOUND),
            Arguments.of(404, HttpStatus.NOT_FOUND),
            Arguments.of(405, HttpStatus.METHOD_NOT_ALLOWED),
            Arguments.of(500, HttpStatus.INTERNAL_SERVER_ERROR),
            Arguments.of(501, HttpStatus.NOT_IMPLEMENTED)
        );
    }
}
