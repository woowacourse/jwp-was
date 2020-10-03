package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import exceptions.InvalidHttpRequestException;

class HttpVersionTest {

    @DisplayName("유효한 HTTP 버전")
    @ParameterizedTest
    @MethodSource("provideHttpVersion")
    void of(String version, HttpVersion httpVersion) {
        assertThat(HttpVersion.from(version)).isEqualTo(httpVersion);
    }

    private static Stream<Arguments> provideHttpVersion() {
        return Stream.of(
            Arguments.of("HTTP/1.1", HttpVersion.HTTP1_1),
            Arguments.of("HTTP/2.0", HttpVersion.HTTP2_0)
        );
    }

    @DisplayName("유효하지 않은 HTTP 버전 - 예외 발생")
    @Test
    void invalid_HttpVersion_Should_Throw_Exception() {
        assertThatThrownBy(() -> HttpVersion.from("HTTP123"))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }
}
