package was.http;

import server.http.io.HttpHandler;
import server.http.request.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHandlerTest {
    @Test
    void parseHttpRequest() throws IOException {
        // TODO: 테스트 세분화
        String request = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = HttpHandler.parse(in);
        assertThat(httpRequest.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getPath()).isEqualTo("/create");
        assertThat(httpRequest.getParam("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParam("password")).isEqualTo("password");
        assertThat(httpRequest.getParam("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParam("email")).isEqualTo("javajigi@slipp.net");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }
}
