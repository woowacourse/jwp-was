package http.request;

import http.common.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {
    private RequestLine requestLine;

    @BeforeEach
    void setUp() throws Exception {
        requestLine = RequestLine.of("GET /user/create?userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1");
    }

    @Test
    public void getMethodTest() {
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void getPathTest() {
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
    }

    @Test
    public void getQueryStringTest() {
        assertThat(requestLine.getQueryString()).isEqualTo("userId=aiden&password=password&name=aiden&email=aiden@aiden.com");
    }

    @Test
    public void getProtocolTest() {
        assertThat(requestLine.getProtocol()).isEqualTo("http");
    }

    @Test
    public void getQueryTest() {
        assertThat(requestLine.getQuery().get("userId")).isEqualTo("aiden");
        assertThat(requestLine.getQuery().get("password")).isEqualTo("password");
        assertThat(requestLine.getQuery().get("name")).isEqualTo("aiden");
        assertThat(requestLine.getQuery().get("email")).isEqualTo("aiden@aiden.com");
    }
}