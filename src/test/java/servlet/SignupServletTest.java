package servlet;

import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.WebServer;

import static org.assertj.core.api.Assertions.assertThat;

class SignupServletTest {
    private HttpTestClient httpTestClient = new HttpTestClient(WebServer.DEFAULT_PORT);

    @Test
    void get() {
        final String response = httpTestClient.get().uri("/signup")
                .exchange();

        assertThat(response).contains("사용자 아이디");
    }

    @Test
    void post() {
        final String response = httpTestClient.post().uri("/signup")
                .body("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net")
                .exchange();

        assertThat(response).contains("HTTP/1.1 302 Found");
    }


}