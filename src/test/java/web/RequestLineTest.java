package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    @DisplayName("요청 받은 requestLine 으로 method, path, parameters, protocol을 가지는 RequestLine을 생성할 수 있다.")
    @Test
    void constructRequestLine() {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        final Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("userId", "javajigi");
        expectedParameters.put("password", "password");
        expectedParameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expectedParameters.put("email", "javajigi%40slipp.net");

        final RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getParameters()).isEqualTo(expectedParameters);
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("요청 받은 requestLine에 parameters가 전달되지 않았을 때 parameters는 null이 된다.")
    @Test
    void constructRequestLineWithNoParameter() {
        String request = "GET /user/create HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);

        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getParameters()).isEqualTo(null);
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
    }
}