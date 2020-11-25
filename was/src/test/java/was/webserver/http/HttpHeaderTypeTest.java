package was.webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpHeaderTypeTest {
    static Stream<Arguments> httpHeaderTypeSource() {
        return Stream.of(
                Arguments.of("Content-Type", HttpHeaderType.CONTENT_TYPE),
                Arguments.of("Content-Length", HttpHeaderType.CONTENT_LENGTH),
                Arguments.of("Location", HttpHeaderType.LOCATION),
                Arguments.of("Connection", HttpHeaderType.CONNECTION),
                Arguments.of("Set-Cookie", HttpHeaderType.SET_COOKIE)
        );
    }

    @DisplayName("입력한 HttpHeaderType 반환 테스트")
    @ParameterizedTest
    @MethodSource("httpHeaderTypeSource")
    void fromTest(String input, HttpHeaderType actual) {
        HttpHeaderType expected = HttpHeaderType.from(input);

        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("지원하지 않는 HttpHeaderType 입력 시 예외 반환")
    @Test
    void fromWithInvalidHttpHeaderTypeTest() {
        assertThatThrownBy(() -> {
            HttpHeaderType.from("invalid http header type");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
