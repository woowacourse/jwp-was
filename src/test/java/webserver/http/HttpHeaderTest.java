package webserver.http;

import exception.InvalidHttpMessageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderField;
import webserver.http.request.HttpResourceType;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {
    private static final String NEW_LINE = System.lineSeparator();

    @DisplayName("HTTP Header가 규격에 맞을 때 HttpHeader 객체 생성")
    @Test
    void httpHeaderFromBufferedReaderTest() throws IOException {
        String headerLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*" + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(headerLines);

        assertThat(HttpHeader.from(br)).isInstanceOf(HttpHeader.class);
    }

    @DisplayName("HTTP Header가 규격에 맞을 때 HttpHeader 객체 생성")
    @Test
    void httpHeaderFromMapTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.CONTENT_LENGTH.getName(), "123");
        headers.put(HttpHeaderField.CONTENT_TYPE.getName(), HttpResourceType.JS.getContentType());

        assertThat(HttpHeader.from(headers)).isInstanceOf(HttpHeader.class);
    }

    @DisplayName("HTTP Header가 규격에 맞지 않을 때 HttpHeader 객체 생성을 시도하면 InvalidHttpMessageException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Connection > keep-alive", "Content-Length 59", "Host: ", ":application/json", "   :   "})
    void httpHeaderFromBufferedReaderExceptionTest(String invalidHeaderLine) {
        String headerLine = invalidHeaderLine + NEW_LINE +
                NEW_LINE;

        BufferedReader br = createBufferedReader(headerLine);

        assertThatThrownBy(() -> HttpHeader.from(br))
                .isInstanceOf(InvalidHttpMessageException.class)
                .hasMessageStartingWith("잘못된 형식의 HTTP Message입니다! -> ");
    }

    @DisplayName("HTTP Header가 규격에 맞지 않을 때 HttpHeader 객체 생성을 시도하면 InvalidHttpMessageException 발생")
    @ParameterizedTest
    @CsvSource(value = {" , ", "Content-Length,", ",application/json"})
    void httpHeaderFromMapExceptionTest(String headerName, String headerValue) {
        Map<String, String> invalidHeaders = new HashMap<>();
        invalidHeaders.put(headerName, headerValue);

        assertThatThrownBy(() -> HttpHeader.from(invalidHeaders))
                .isInstanceOf(InvalidHttpMessageException.class)
                .hasMessageStartingWith("잘못된 형식의 HTTP Message입니다! -> ");
    }

    @DisplayName("HttpHeader를 통해 HttpBody 객체를 반환")
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
        HttpHeader httpHeader = HttpHeader.from(headerReader);
        BufferedReader bodyReader = createBufferedReader(requestBody);

        assertThat(httpHeader.createHttpBody(bodyReader)).isInstanceOf(HttpBody.class);
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
        HttpHeader httpHeader = HttpHeader.from(headerReader);
        BufferedReader br = createBufferedReader(requestEmptyBody);

        assertThat(httpHeader.createHttpBody(br)).isInstanceOf(HttpBody.class);
    }

    @DisplayName("HttpHeader에 존재하는 특정 필드의 값을 요청하면 정상적으로 반환")
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
        HttpHeader httpHeader = HttpHeader.from(br);

        assertThat(httpHeader.getHeaderValue(headerField)).isEqualTo(headerValue);
    }

    @DisplayName("HttpHeader에 존재하지 않는 특정 필드의 값을 요청하면 null 반환")
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
        HttpHeader httpHeader = HttpHeader.from(br);

        assertThat(httpHeader.getHeaderValue(notExistHeaderField)).isNull();
    }

    private BufferedReader createBufferedReader(String content) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}