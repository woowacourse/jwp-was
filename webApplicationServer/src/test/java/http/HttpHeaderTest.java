package http;

import exception.InvalidHttpMessageException;
import http.header.HttpHeader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeaderTest {
    @DisplayName("HTTP Header가 규격에 맞을 때 HttpHeader 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"Content-Length: 1234", "Content-Type: application/javascript"})
    void httpHeaderFromTest(String header) {
        assertThat(HttpHeader.from(header)).isInstanceOf(HttpHeader.class);
    }

    @DisplayName("HTTP Header가 규격에 맞을 때 HttpHeader 객체 생성")
    @ParameterizedTest
    @CsvSource(value = {"Connection,keep-alive", "Host,localhost:8080"})
    void httpHeaderOfTest(String name, String value) {
        assertThat(HttpHeader.of(name, value)).isInstanceOf(HttpHeader.class);
    }

    @DisplayName("HTTP Header가 규격에 맞지 않을 때 HttpHeader 객체 생성을 시도하면 InvalidHttpMessageException 발생")
    @ParameterizedTest
    @CsvSource(value = {" , ", "Content-Length,", ",application/json"})
    void httpHeaderOfExceptionTest(String name, String value) {
        Assertions.assertThatThrownBy(() -> HttpHeader.of(name, value))
                .isInstanceOf(InvalidHttpMessageException.class)
                .hasMessageStartingWith("잘못된 형식의 HTTP Message입니다! -> ");
    }
}
