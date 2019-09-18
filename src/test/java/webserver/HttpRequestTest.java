package webserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void InputStream_빈_문자열() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        HttpRequest httpRequest = new HttpRequest(in);

        assertThat(httpRequest.getRequestContents()).isEqualTo("");
    }

    @Test
    void InputStream_GET_확인() {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);

        assertThat(httpRequest.getMethod()).isEqualTo(RequestMethod.GET);
    }

    @Test
    void InputStream_Path_확인() {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);

        assertThat(httpRequest.getRequestContents()).isEqualTo(request);
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    @Test
    void InputStream_QueryString_확인() throws UnsupportedEncodingException {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);

        assertThat(httpRequest.getQueryString()).isEqualTo("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
    }

}