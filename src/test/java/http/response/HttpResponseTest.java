package http.response;

import http.common.HttpCookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    private HttpResponse httpResponse;

    @BeforeEach
    public void setUp() {
        HttpCookie httpCookie = HttpCookie.of();
        httpResponse = HttpResponse.of(httpCookie);
    }

    @Test
    @DisplayName("Header 추가 테스트")
    public void putHeaderTest() {
        httpResponse.putHeader("Connection", "keep-alive");

        assertThat(httpResponse.toString()).contains("Connection: keep-alive");
    }

    @Test
    @DisplayName("Body 추가 테스트")
    public void sendOKTest() {
        String body = "HELLO!";
        httpResponse.sendOk(body.getBytes(), "test.html");

        assertThat(httpResponse.getBody()).isEqualTo(body.getBytes());
        assertThat(httpResponse.toString()).contains("200 OK");
        assertThat(httpResponse.toString()).contains("Content-Type: text/html;charset=utf-8");
    }

    @Test
    @DisplayName("redirect 테스트")
    public void redirectTest() {
        httpResponse.redirect("index.html");

        assertThat(httpResponse.toString()).contains("302 FOUND");
        assertThat(httpResponse.toString()).contains("Location: index.html");
    }

    @Test
    @DisplayName("body byte[] 테스트")
    public void getByteTest() {
        String body = "HELLO!";
        httpResponse.sendOk(body.getBytes(), "test.html");

        assertThat(httpResponse.getBody()).contains(body.getBytes());
    }

    @Test
    @DisplayName("NotFound 테스트")
    public void notFoundTest() {
        httpResponse.sendNotFound();

        assertThat(httpResponse.toString()).contains("404 NOT FOUND");
    }

    @Test
    @DisplayName("NotAllowed 테스트")
    public void sendNotAllowedTest() {
        httpResponse.sendNotAllowed();

        assertThat(httpResponse.toString()).contains("405 Method Not Allowed");
    }
}