package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpStatus;
import webserver.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseStatusLineTest {
    private static final String GET_REQUEST_MESSAGE =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    @DisplayName("응답 상태라인 생성 확인")
    @Test
    void of() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        ResponseStatusLine statusLine = ResponseStatusLine.of(httpRequest, HttpStatus.OK);
        assertThat(statusLine.response()).isEqualTo("HTTP/1.1 200 OK\r\n");
    }
}
