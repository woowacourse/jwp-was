package http.request;

import http.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRequestTest {

    @Test
    @DisplayName("리퀘스트 라인 테스트")
    void requestLine() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Cache-Control: max-age=0",
                ""));
        assertThat(httpRequest.getHttpRequestLine().toString()).isEqualTo("GET /index.html HTTP/1.1\r\n");
    }

    @Test
    @DisplayName("Body가 있는 경우 테스트")
    void contentLength() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Cache-Control: max-age=0",
                "Content-Length: 20",
                "",
                "userId=abcd&userName=abc\r\n"));
        assertThat(httpRequest.getHttpRequestBody()).isEqualTo(Collections.singletonList("userId=abcd&userName=abc\r\n"));
    }

    @Test
    @DisplayName("확장자 있는 경우 테스트")
    void isContainExtension() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Cache-Control: max-age=0",
                ""));
        assertTrue(httpRequest.isContainExtension());
    }

    @Test
    @DisplayName("확장자 없는 경우 테스트")
    void isNotContainExtension() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Cache-Control: max-age=0",
                "Content-Length: 20",
                "",
                "userId=abcd&userName=abc\r\n"));
        assertFalse(httpRequest.isContainExtension());
    }

    @Test
    @DisplayName("리퀘스트라인에서 Method 추출")
    void getMethod() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Cache-Control: max-age=0",
                ""));
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("리퀘스트라인에서 Path 추출")
    void getUri() {
        HttpRequest httpRequest = new HttpRequest(Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Cache-Control: max-age=0",
                ""));
        assertThat(httpRequest.getUri()).isEqualTo("/index.html");
    }
}