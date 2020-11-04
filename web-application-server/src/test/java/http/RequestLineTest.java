package http;

import exception.InvalidRequestLineException;
import http.request.RequestLine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @DisplayName("정해둔 Request Line 규격에 맞는 값으로 RequestLine 객체 생성")
    @Test
    void fromTest() {
        String requestLine = "GET /index.html HTTP/1.1";

        assertThat(RequestLine.from(requestLine)).isInstanceOf(RequestLine.class);
    }

    @DisplayName("정해둔 Request Line 규격에 맞지 않는 값으로 RequestLine 객체를 생성하면 InvalidRequestLineException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"GET /index.htmlHTTP/1.1", "POST"})
    void fromExceptionTest(String invalidRequestLine) {
        Assertions.assertThatThrownBy(() -> RequestLine.from(invalidRequestLine))
                .isInstanceOf(InvalidRequestLineException.class)
                .hasMessage("Request Line이 규격에 맞지 않습니다 -> " + invalidRequestLine);
    }
}