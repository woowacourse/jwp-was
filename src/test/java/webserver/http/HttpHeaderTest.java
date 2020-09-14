package webserver.http;

import exception.InvalidHttpMessageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.body.HttpBody;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {
    private static final String NEW_LINE = System.lineSeparator();

    @DisplayName("HTTP Request Header가 규격에 맞을 때 HttpHeader 객체 생성")
    @Test
    void httpHeaderBuilderTest() {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*";

        assertThat(createHttpHeader(requestHeaderLines)).isInstanceOf(HttpHeader.class);
    }

    @DisplayName("HTTP Request Header가 규격에 맞지 않을 때 HttpHeader 객체 생성을 시도하면 InvalidHttpMessageException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"Connection > keep-alive", "Content-Length 59", "Host: ", ":application/json", "   :   "})
    void httpHeaderBuilderExceptionTest(String invalidHeaderLine) {
        HttpHeader.Builder builder = new HttpHeader.Builder();

        assertThatThrownBy(() -> builder.addHeaderLine(invalidHeaderLine))
                .isInstanceOf(InvalidHttpMessageException.class)
                .hasMessage("잘못된 형식의 HTTP Message입니다! -> " + invalidHeaderLine);
    }

    @DisplayName("HttpHeader를 통해 HttpBody 객체를 반환")
    @Test
    void createHttpBodyTest() throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Content-Length: 59" + NEW_LINE +
                "Content-Type: application/x-www-form-urlencoded" + NEW_LINE +
                "Accept: */*";
        String requestBody = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi" +
                "%40slipp.net";

        HttpHeader httpHeader = createHttpHeader(requestHeaderLines);
        BufferedReader br = createBufferedReader(requestBody);

        assertThat(httpHeader.createHttpBody(br)).isInstanceOf(HttpBody.class);
    }

    @DisplayName("HTTP Request에 Body가 없는 경우에도 HttpBody 객체를 반환")
    @Test
    void createEmptyHttpBodyTest() throws IOException {
        String requestHeaderLines = "Host: localhost:8080" + NEW_LINE +
                "Connection: keep-alive" + NEW_LINE +
                "Accept: */*";
        String requestEmptyBody = "";

        HttpHeader httpHeader = createHttpHeader(requestHeaderLines);
        BufferedReader br = createBufferedReader(requestEmptyBody);

        assertThat(httpHeader.createHttpBody(br)).isInstanceOf(HttpBody.class);
    }

    private HttpHeader createHttpHeader(String requestHeaderLines) {
        String[] headerLines = requestHeaderLines.split(NEW_LINE);
        HttpHeader.Builder builder = new HttpHeader.Builder();
        for (String headerLine : headerLines) {
            builder.addHeaderLine(headerLine);
        }
        return builder.build();
    }

    private BufferedReader createBufferedReader(String requestBody) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}