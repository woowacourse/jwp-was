package request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

    @Test
    @DisplayName("HTTP 요청 읽기")
    void readHttpRequest() throws IOException {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n\n"
            + "id=3456789";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        HttpRequest httpRequest = HttpRequest.readHttpRequest(testInput);

        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getUriPath()).isEqualTo("/join");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("10");
    }

    @Test
    @DisplayName("HTTP 요청 읽기 - request line 형식이 잘못된 경우 예외처리")
    void readHttpRequest_IfRequestLineFormatIsWring_ThrowException() {
        String httpRequestFormat = "GGGGET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n\n"
            + "id=3456789";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("request line input is unformatted.");
    }

    @Test
    @DisplayName("HTTP 요청 읽기 - header 형식이 잘못된 경우 예외처리")
    void readHttpRequest_IfRequestHeaderFormatIsWring_ThrowException() {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host; localhost:8080\n\n";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("input header format : Host; localhost:8080 is wrong.");
    }

    @Test
    @DisplayName("HTTP 요청 읽기 - request 마지막에 있어야하는 공백라인이 없을 때 예외처리")
    void readHttpRequest_IfEndSpaceIsNotExist_ThrowException() {
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        assertThatThrownBy(() -> HttpRequest.readHttpRequest(testInput))
            .isInstanceOf(Exception.class)
            .hasMessage("an empty line must exist at the end of the request.");
    }
}
