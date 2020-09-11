package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @DisplayName("요청 받은 requestLine 으로 RequestLine을 생성할 수 있다.")
    @Test
    void parseMethod() {
        String request = "GET /index.html?a=a HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/index.html?a=a");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("requestLine 에서 요청 url만 추출할 수 있다.")
    @Test
    void parseURL() {
        String request = "GET /index.html?a=a HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.requestUrl()).isEqualTo("/index.html");
    }

    @DisplayName("requestLine 에서 전달받은 파라미터를 추출할 수 있다.")
    @Test
    void parseParameters() {
        String request = "GET /user/create?userId=a&password=b&name=c&email=d%40d HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);
        final Map<String, String> parameters = requestLine.parseParameters();

        assertThat(parameters.keySet()).containsOnly("userId", "password", "name", "email");
        assertThat(parameters.values()).containsOnly("a", "b", "c", "d%40d");
    }

}