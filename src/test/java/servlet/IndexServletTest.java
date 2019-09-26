package servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.http.HttpStatus;

class IndexServletTest {
    private HttpTestClient httpTestClient;

    @BeforeEach
    void setUp() {
        httpTestClient = new HttpTestClient(HttpTestClient.DEFAULT_PORT);
    }

    @Test
    void 인덱스_페이지_호출() {
        httpTestClient.get().uri("/")
                .exchange()
                .matchHttpStatus(HttpStatus.OK)
                .containsBody("runtime 에");
    }
}