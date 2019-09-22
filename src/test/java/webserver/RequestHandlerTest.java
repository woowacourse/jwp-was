package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    private HttpTestClient httpTestClient;

    @BeforeEach
    void setUp() throws IOException {
        httpTestClient = new HttpTestClient(WebServer.DEFAULT_PORT);
    }

    @Test
    void static_파일_요청() {
        // given
        final String plainTextRequest = "GET /css/styles.css HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: text/css,*/*;q=0.1\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n";

        // when
        final String response = httpTestClient.send(plainTextRequest);

        // then
        assertThat(response).contains("background-color:#e0e0e0;");
    }

    @Test
    void servlet_요청() {
        // given
        final String plainTextRequest = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        final String response = httpTestClient.send(plainTextRequest);

        // then

    }
}