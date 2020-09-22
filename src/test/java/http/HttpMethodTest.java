package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HttpMethodTest {
    @ParameterizedTest
    @MethodSource(value = {"provideValidMethod"})
    @DisplayName("HttpMethod 확인 테스트")
    void from(String method, HttpMethod expected) {
        assertThat(HttpMethod.from(method)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideValidMethod() {
        return Stream.of(
            Arguments.of("GET", HttpMethod.GET),
            Arguments.of("POST", HttpMethod.POST)
        );
    }
}
