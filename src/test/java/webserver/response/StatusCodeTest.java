package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.NotFoundStatusCodeException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StatusCodeTest {

    private static Stream<Arguments> provideStatusCodeForFind() {
        return Stream.of(
            Arguments.of("100", StatusCode.CONTINUE),
            Arguments.of("200", StatusCode.OK),
            Arguments.of("201", StatusCode.CREATED),
            Arguments.of("202", StatusCode.ACCEPTED),
            Arguments.of("204", StatusCode.NO_CONTENT),
            Arguments.of("301", StatusCode.MOVED_PERMANENTLY),
            Arguments.of("302", StatusCode.FOUND),
            Arguments.of("400", StatusCode.BAD_REQUEST),
            Arguments.of("401", StatusCode.UNAUTHORIZED),
            Arguments.of("403", StatusCode.FORBIDDEN),
            Arguments.of("404", StatusCode.NOT_FOUND),
            Arguments.of("405", StatusCode.METHOD_NOT_ALLOWED),
            Arguments.of("500", StatusCode.INTERNAL_SERVER_ERROR)
        );
    }

    @DisplayName("StatusCode 찾기 - 성공")
    @ParameterizedTest
    @MethodSource("provideStatusCodeForFind")
    void find(String input, StatusCode statusCode) {
        assertThat(StatusCode.find(input)).isEqualTo(statusCode);
    }

    @DisplayName("StatusCode 찾기 - 예외, 해당하는 코드를 찾지 못함")
    @Test
    void find_NotExistStatusCode_ThrownException() {
        String statusCode = "1000";
        assertThatThrownBy(() -> StatusCode.find(statusCode))
            .isInstanceOf(NotFoundStatusCodeException.class);
    }
}
