package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.InvalidRequestHeaderException;

class RequestHeaderTest {
    private static final String VALID_HEADER = "GET /index.html?name=dd&password=ddd HTTP/1.1\n"
        + "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
        + "Accept: */*\n";


    @DisplayName("Request header를 정상적으로 추출한다.")
    @Test
    void of() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("Host", "localhost:8080");
        expected.put("Connection", "keep-alive");
        expected.put("Accept", "*/*");
        Map<String, String> expectedParams = new LinkedHashMap<>();
        expectedParams.put("name", "dd");
        expectedParams.put("password", "ddd");

        RequestHeader header = RequestHeader.of(VALID_HEADER);
        Map<String, String> actual = header.getHeaders();
        Map<String, String> params = header.getQueryParams();

        assertAll(
            () -> assertThat(header.getMethod()).isEqualTo(RequestHeader.MethodType.GET),
            () -> assertThat(header.getPath()).isEqualTo("/index.html"),
            () -> assertThat(header.getProtocolVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(actual).isEqualTo(expected),
            () -> assertThat(params).isEqualTo(expectedParams)
        );
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

    @DisplayName("QueryParams에 정보가 없는 경우 params가 비어있다.")
    @Test
    void emptyQueryParams() {
        String INVALID_HEADER = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        RequestHeader header = RequestHeader.of(INVALID_HEADER);
        Map<String, String> queryParams = header.getQueryParams();

        assertThat(queryParams).isEmpty();
    }

    @DisplayName("header에 정보가 없는 경우 빈 헤더를 반환한다.")
    @Test
    void emptyAttribute() {
        String INVALID_HEADER = "GET /index.html HTTP/1.1\n";
        RequestHeader header = RequestHeader.of(INVALID_HEADER);
        Map<String, String> attribute = header.getHeaders();

        assertThat(attribute).isEmpty();
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
