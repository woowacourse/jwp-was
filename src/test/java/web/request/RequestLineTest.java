package web.request;

import exception.InvalidRequestLineException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class RequestLineTest {
    @Test
    @DisplayName("RequestLine 객체가 올바르게 생성된다")
    void createRequestLineTest() {
        String parameters = "GET /index.html HTTP/1.1";

        RequestLine requestLine = new RequestLine(parameters);

        Assertions.assertThat(requestLine.getMethod().getName()).isEqualTo("GET");
        Assertions.assertThat(requestLine.getRequestPath().getTarget()).isEqualTo("/index.html");
        Assertions.assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");

    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예외 테스트: RequestLine 생성 중 잘못된 값이 전달되면, 예외를 발생시킨다.")
    void createHttpRequestExceptionTest(String invalidValue) {
        Assertions.assertThatThrownBy(() -> new RequestLine(invalidValue))
                .isInstanceOf(InvalidRequestLineException.class)
                .hasMessage("Request Line의 값이 올바르지 않습니다");
    }
}
