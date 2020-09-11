package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUtilsTest {

    @DisplayName("requestLind 에서 Method를 추출할 수 있다.")
    @Test
    void parseMethod() {
        String requestLine = "GET /index.html?a=a HTTP/1.1";
        String method = RequestUtils.parseMethod(requestLine);

        assertThat(method).isEqualTo("GET");
    }

    @DisplayName("requestLine 에서 path를 추출할 수 있다.")
    @Test
    void parseURL() {
        String requestLine = "GET /index.html?a=a HTTP/1.1";
        String path = RequestUtils.parseURL(requestLine);

        assertThat(path).isEqualTo("/index.html");
    }

    @DisplayName("requestLine 에서 User 추출할 수 있다.")
    @Test
    void parseUser() {
        String requestLine = "GET /user/create?userId=a&password=b&name=c&email=d%40d HTTP/1.1";
        final User expected = new User("a", "b", "c", "d%40d");

        final User user = RequestUtils.parseUser(requestLine);

        assertThat(user).isEqualTo(expected);
    }

    @DisplayName("body 에서 User를 추출할 수 있다.")
    @Test
    void parseBody() {
        String body = "userId=a&password=b&name=c&email=d%40d";
        final User expected = new User("a", "b", "c", "d%40d");

        final User user = RequestUtils.parseBody(body);

        assertThat(user).isEqualTo(expected);
    }

    @DisplayName("요청 받은 정보 리스에서 리스트에서 headers 를 추출할 수 있다.")
    @Test
    void parseHeader() {
        final ArrayList<String> requestHeaders = new ArrayList<>();
        requestHeaders.add("Host: localhost:8080");
        requestHeaders.add("Connection: keep-alive");
        requestHeaders.add("Content-Length: 59");

        Map<String, String> headers = RequestUtils.parseHeaders(requestHeaders);

        assertThat(headers.keySet()).containsOnly("Host", "Connection", "Content-Length");
        assertThat(headers.values()).containsOnly("localhost:8080", "keep-alive", "59");
    }
}