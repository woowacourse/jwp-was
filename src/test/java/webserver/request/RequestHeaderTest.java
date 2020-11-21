package webserver.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.InvalidRequestHeaderException;

class RequestHeaderTest {
    private static final String VALID_HEADER = "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
        + "Accept: */*\n";

    @DisplayName("Request header를 정상적으로 추출한다.")
    @Test
    void of() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("Host", "localhost:8080");
        expected.put("Connection", "keep-alive");
        expected.put("Accept", "*/*");

        RequestHeader header = RequestHeader.of(VALID_HEADER);
        Map<String, String> actual = header.getHeaders();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("메소드 타입이 잘못된 경우 예외를 반환합니다.")
    @Test
    void methodInvalid() {
        String INVALID_HEADER = "HI /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";

        assertThatThrownBy(() -> RequestHeader.of(INVALID_HEADER))
            .isInstanceOf(InvalidRequestHeaderException.class)
            .hasMessage("지원하지 않는 request header 형식입니다.");
    }

    @DisplayName("header 값이 존재하면 값을 반환하고, 없으면 null을 반환한다.")
    @Test
    void getOrDefault() {
        RequestHeader header = RequestHeader.of(VALID_HEADER);

        assertAll(
            () -> assertThat(header.getHeader("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(header.getHeader("NOT_EXIST_KEY")).isNull()
        );
    }

    @DisplayName("request.txt header를 문자열의 형태로 정상 반환한다.")
    @Test
    void toStringTest() {
        RequestHeader header = RequestHeader.of(VALID_HEADER);

        assertThat(header.toString()).isEqualTo(VALID_HEADER);
    }
}
