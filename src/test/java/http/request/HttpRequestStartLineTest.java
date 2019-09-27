package http.request;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestStartLineTest {

    @Test
    void get_생성() throws UnsupportedEncodingException {
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.of("GET /index.html HTTP/1.1");
        assertThat(httpRequestStartLine.getHttpMethodType()).isEqualTo(HttpMethodType.GET);
        assertThat(httpRequestStartLine.getPath()).isEqualTo("/index.html");
    }

    @Test
    void get_파라미터포함_생성() throws UnsupportedEncodingException {
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

        assertThat(httpRequestStartLine.getHttpMethodType()).isEqualTo(HttpMethodType.GET);
        assertThat(httpRequestStartLine.getPath()).isEqualTo("/user/create");
        assertThat(httpRequestStartLine.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequestStartLine.getParameter("password")).isEqualTo("password");
        assertThat(httpRequestStartLine.getParameter("name")).isEqualTo("박재성");
        assertThat(httpRequestStartLine.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void post_생성() throws UnsupportedEncodingException {
        HttpRequestStartLine httpRequestStartLine = HttpRequestStartLine.of("POST /user/create HTTP/1.1");

        assertThat(httpRequestStartLine.getHttpMethodType()).isEqualTo(HttpMethodType.POST);
        assertThat(httpRequestStartLine.getPath()).isEqualTo("/user/create");
    }

}
