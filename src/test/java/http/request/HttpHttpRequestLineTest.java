package http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpHttpRequestLineTest {

    private HttpRequestLine httpRequestLine;

    @BeforeEach
    void setUp() {
        httpRequestLine = new HttpRequestLine("GET", new HttpRequestUrl("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"), "HTTP/1.1");
    }

    @Test
    void getMethod() {
        assertThat(httpRequestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    void getUri() {
        assertThat(httpRequestLine.getUrl()).isEqualTo("/user/create");
    }

    @Test
    void getValue() {
        assertThat(httpRequestLine.getValue("userId")).isEqualTo("javajigi");
        assertThat(httpRequestLine.getValue("password")).isEqualTo("password");
        assertThat(httpRequestLine.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(httpRequestLine.getValue("email")).isEqualTo("javajigi%40slipp.net");
    }
}