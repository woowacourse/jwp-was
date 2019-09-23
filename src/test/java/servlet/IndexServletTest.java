package servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.WebServer;
import webserver.http.HttpStatus;

import java.io.IOException;

class IndexServletTest {
    private HttpTestClient httpTestClient;

    @BeforeEach
    void setUp()  {
        httpTestClient = new HttpTestClient(WebServer.DEFAULT_PORT);
    }

    @Test
    void get() {
        httpTestClient.get().uri("/")
                .exchange()
                .matchHttpStatus(HttpStatus.OK)
                .containsBody("runtime 에");
    }
}