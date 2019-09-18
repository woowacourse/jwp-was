package webserver.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    @DisplayName("Header에서 html URL 추출")
    public void extractUrl() {
        Request request = new Request("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");

        assertThat(request.extractUrl()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Header에서 루트 URL 추출")
    public void extractUrl1() {
        Request request = new Request("GET / HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*");

        assertThat(request.extractUrl()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("Header에서 Query Parameter 추출")
    public void extractQueryParameter() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        Request request = new Request(
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n");

        assertThat(request.extractQueryParameter(request.extractUrl())).isEqualTo(expected);
    }
}