package http;

import http.request.RequestLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    private RequestLine requestLine;

    @BeforeEach
    void setUp() {
        requestLine = new RequestLine(
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n");
    }

    @Test
    @DisplayName("Request Line에서 Query Parameter 추출")
    public void extractQueryParameter() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "javajigi");
        expected.put("password", "password");
        expected.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        expected.put("email", "javajigi%40slipp.net");

        assertThat(requestLine.extractQueryParameter()).isEqualTo(expected);
    }
}
