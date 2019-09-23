package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.http.HttpStatus;

import java.io.IOException;

class RequestHandlerTest {
    private HttpTestClient httpTestClient;

    @BeforeEach
    void setUp() throws IOException {
        httpTestClient = new HttpTestClient(WebServer.DEFAULT_PORT);
    }

    @Test
    void static_파일_요청() {
        httpTestClient.get()
                .uri("/css/styles.css")
                .exchange()
                .matchHttpStatus(HttpStatus.OK)
                .containsBody("background-color:#e0e0e0;");
    }
}