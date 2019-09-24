package servlet;

import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;

class SignupServletTest {
    private HttpTestClient httpTestClient = new HttpTestClient(HttpTestClient.DEFAULT_PORT);

    @Test
    void get() {
        httpTestClient.get().uri("/user/create")
                .exchange()
                .matchHttpStatus(HttpStatus.OK)
                .containsBody("사용자 아이디");
    }

    @Test
    void post() {
        httpTestClient.post().uri("/user/create")
                .body("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .exchange()
                .matchHttpStatus(HttpStatus.FOUND)
                .matchHeader(HttpHeaders.LOCATION, "/");
    }
}