package http.request;

import http.common.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    private RequestLine requestLine;

    @BeforeEach
    void setUp() throws Exception {
        requestLine = RequestLine.of("GET /user/create?userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1");
    }

    @Test
    @DisplayName("요청 method 테스트")
    public void getMethodTest() {
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("요청 path 테스트")
    public void getPathTest() {
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("요청 query 테스트")
    public void getQueryStringTest() {
        assertThat(requestLine.getQueryString()).isEqualTo("userId=aiden&password=password&name=aiden&email=aiden@aiden.com");
    }

    @Test
    @DisplayName("요청 Protocol 테스트")
    public void getProtocolTest() {
        assertThat(requestLine.getProtocol()).isEqualTo("http");
    }
}