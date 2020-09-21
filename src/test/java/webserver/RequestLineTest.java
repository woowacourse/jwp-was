package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.NotExistRequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestLineTest {

    @DisplayName("RequestLine 생성 - 예외, Null And Empty")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor_NullAndEmpty_ThrownException(String input) {
        assertThatThrownBy(() -> new RequestLine(input)).isInstanceOf(NotExistRequestHeader.class);
    }

    @DisplayName("RequestLine 생성 - 예외, RequestLine의 사이즈가 맞지 않음")
    @ParameterizedTest
    @ValueSource(strings = {"GET / HTTP/1.1 hello", "GET /"})
    void constructor_NegativeSize_ThrownException(String input) {
        assertThatThrownBy(() -> new RequestLine(input)).isInstanceOf(NegativeArraySizeException.class);
    }

    @DisplayName("RequestHeaderFirstLine에서 http method 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, GET", "POST /index.html HTTP/1.1, POST"})
    void getMethod(String input, String expected) {
        RequestLine requestLine = new RequestLine(input);
        assertThat(requestLine.getMethod()).isEqualTo(expected);
    }

    @DisplayName("HttpHeader first line에서 리소스 경로 추출")
    @ParameterizedTest
    @CsvSource(value = {"GET / HTTP/1.1, /index.html", "GET /index.html HTTP/1.1, /index.html"})
    void extractResourcePath(String input, String expected) {
        RequestLine requestLine = new RequestLine(input);
        assertThat(requestLine.getPath()).isEqualTo(expected);
    }
}
