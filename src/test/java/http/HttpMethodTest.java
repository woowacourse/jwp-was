package http;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import exceptions.InvalidHttpRequestException;

class HttpMethodTest {

    @DisplayName("유혀하지 않은 HTTP 메서드 - 예외 발생")
    @Test
    void invalid_HttpMethod_Should_Throw_Exception() {
        assertThatThrownBy(() -> HttpMethod.of("INVALID_METHOD"))
            .isInstanceOf(InvalidHttpRequestException.class)
            .hasMessage("유효한 HTTP 요청이 아닙니다.");
    }

    @DisplayName("유효한 HTTP 메서드")
    @ParameterizedTest
    @MethodSource("provideHttpMethod")
    void valid_HttpMethod(String method, HttpMethod httpMethod) {
        assertThat(HttpMethod.of(method)).isEqualTo(httpMethod);
    }

    private static Stream<Arguments> provideHttpMethod() {
        return Stream.of(
            Arguments.of("GET", HttpMethod.GET),
            Arguments.of("POST", HttpMethod.POST),
            Arguments.of("PUT", HttpMethod.PUT),
            Arguments.of("PATCH", HttpMethod.PATCH),
            Arguments.of("DELETE", HttpMethod.DELETE)
        );
    }
}
