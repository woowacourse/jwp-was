package http;

import exception.InvalidHttpMessageException;
import http.body.HttpBody;
import http.header.HttpHeaders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    private static final String NEW_LINE = System.lineSeparator();

    @DisplayName("HTTP Headers가 규격에 맞을 때 HttpHeaders 객체 생성")
    @Test
    void httpHeaderFromBufferedReaderTest() throws IOException {
        String headerLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(headerLines);

        assertThat(HttpHeaders.from(br)).isInstanceOf(HttpHeaders.class);
    }

    @DisplayName("HTTP Headers가 규격에 맞지 않을 때 HttpHeaders 객체 생성을 시도하면 InvalidHttpMessageException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Connection > keep-alive", "Content-Length 59", "Host: ", ":application/json", "   :   "})
    void httpHeaderFromBufferedReaderExceptionTest(String invalidHeaderLine) {
        String headerLine = invalidHeaderLine + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(headerLine);

        Assertions.assertThatThrownBy(() -> HttpHeaders.from(br))
                .isInstanceOf(InvalidHttpMessageException.class)
                .hasMessageStartingWith("잘못된 형식의 HTTP Message입니다! -> ");
    }

    @DisplayName("HttpHeaders를 통해 HttpBody 객체를 반환")
    @Test
    void createHttpBodyTest() throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;
        String requestBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi" +
                "%40slipp.net";

        BufferedReader headerReader = createBufferedReader(requestHeaderLines);
        HttpHeaders httpHeaders = HttpHeaders.from(headerReader);
        BufferedReader bodyReader = createBufferedReader(requestBody);

        assertThat(httpHeaders.createHttpBody(bodyReader)).isInstanceOf(HttpBody.class);
    }

    @DisplayName("HTTP Request에 Body가 없는 경우에도 HttpBody 객체를 반환")
    @Test
    void createEmptyHttpBodyTest() throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;
        String requestEmptyBody = "";

        BufferedReader headerReader = createBufferedReader(requestHeaderLines);
        HttpHeaders httpHeaders = HttpHeaders.from(headerReader);
        BufferedReader br = createBufferedReader(requestEmptyBody);

        assertThat(httpHeaders.createHttpBody(br)).isInstanceOf(HttpBody.class);
    }

    @DisplayName("HttpHeaders에 존재하는 특정 필드의 값을 요청하면 정상적으로 반환")
    @ParameterizedTest
    @CsvSource(value = {"Host,localhost:8080", "Connection,keep-alive", "Content-Length,59",
            "Content-Type,application/x-www-form-urlencoded", "Accept,*/*"})
    void getHeaderValueTest(String headerField, String headerValue) throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(requestHeaderLines);
        HttpHeaders httpHeaders = HttpHeaders.from(br);

        assertThat(httpHeaders.getHeaderValue(headerField)).isPresent();
    }

    @DisplayName("HttpHeaders에 존재하지 않는 특정 필드의 값을 요청하면 null 반환")
    @ParameterizedTest
    @ValueSource(strings = {"Cookie", "Cache-Control", "If-None-Match"})
    void getHeaderValueTest(String notExistHeaderField) throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(requestHeaderLines);
        HttpHeaders httpHeaders = HttpHeaders.from(br);

        assertThat(httpHeaders.getHeaderValue(notExistHeaderField)).isNotPresent();
    }

    private BufferedReader createBufferedReader(String content) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}