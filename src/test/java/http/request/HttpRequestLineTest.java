package http.request;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import http.HttpMethod;

public class HttpRequestLineTest {
    @ParameterizedTest
    @MethodSource(value = {"provideValidHRequestLine"})
    @DisplayName("HttpRequestLine 파싱 테스트")
    void from(String requestLine, String method, String path, String version) {
        HttpRequestLine httpRequestLine = HttpRequestLine.from(requestLine);
        assertThat(httpRequestLine.getMethod()).isEqualTo(HttpMethod.from(method));
        assertThat(httpRequestLine.getPath()).isEqualTo(path);
        assertThat(httpRequestLine.getVersion()).isEqualTo(version);
    }

    private static Stream<Arguments> provideValidHRequestLine() {
        return Stream.of(
            Arguments.of("GET /index.html HTTP/1.1", "GET", "/index.html", "HTTP/1.1"),
            Arguments.of("POST / HTTP/1.1", "POST", "/", "HTTP/1.1")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "GET", "GET /index.html"})
    @DisplayName("HttpRequestLine 파싱 예외 테스트")
    void from_exception(String requestLine) {
        assertThatIllegalArgumentException().isThrownBy(() -> HttpRequestLine.from(requestLine));
    }
}
