package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import request.RequestLine;

class RequestLineTest {

    private RequestLine requestLine = new RequestLine("GET /users/me HTTP/1.1");

    @Test
    @DisplayName("생성자")
    void constructor() {
        assertThat(new RequestLine("POST / HTTP/1.1")).isInstanceOf(RequestLine.class);
    }

    @ParameterizedTest
    @MethodSource("getConstructorErrorStateParameter")
    @DisplayName("생성자 - 입력이 잘못된 경우 예외처리")
    void constructor_IfInputIsUnformatted_ThrowException(String wrongInput, String errorMessage) {
        assertThatThrownBy(() -> new RequestLine(wrongInput))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(errorMessage);
    }

    private static Stream<Arguments> getConstructorErrorStateParameter() {
        return Stream.of(
            Arguments.of("POST /home HTTP/1.1 뀨?", "request line input is unformatted."),
            Arguments.of("POT /home HTTP/1.1", "request line input is unformatted.")
        );
    }

    @Test
    @DisplayName("요청 메서드 꺼내기")
    void getMethod() {
        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }
}
