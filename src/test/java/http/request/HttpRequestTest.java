package http.request;

import http.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRequestTest {

    @Test
    @DisplayName("리퀘스트 라인 테스트")
    void requestLine() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Cache-Control", "max-age=0"));
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.empty(), httpRequestBody);

        assertThat(httpRequest.getHttpRequestLine().toString()).isEqualTo("GET /index.html HTTP/1.1\r\n");
    }

    @Test
    @DisplayName("Body가 있는 경우 테스트")
    void contentLength() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("POST /user/create HTTP/1.1");
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        headers.put("Content-Length", "20");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(headers);
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=abcd&userName=abc\r\n".getBytes());
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.of(new String(httpRequestBody.getBody())), httpRequestBody);

        assertThat(httpRequest.getHttpRequestBody()).isEqualTo("userId=abcd&userName=abc\r\n".getBytes());
    }

    @Test
    @DisplayName("확장자 있는 경우 테스트")
    void isContainExtension() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(headers);
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.empty(), httpRequestBody);

        assertTrue(httpRequest.isContainExtension());
    }

    @Test
    @DisplayName("확장자 없는 경우 테스트")
    void isNotContainExtension() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("POST /user/create HTTP/1.1");
        Map<String, String> headers = new HashMap<>();
        headers.put("Cache-Control", "max-age=0");
        headers.put("Content-Length", "20");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(headers);
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=abcd&userName=abc\n".getBytes());
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.of(new String(httpRequestBody.getBody())), httpRequestBody);

        assertFalse(httpRequest.isContainExtension());
    }

    @Test
    @DisplayName("리퀘스트라인에서 Method 추출")
    void getMethod() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Cache-Control", "max-age=0"));
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.empty(), httpRequestBody);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("리퀘스트라인에서 Path 추출")
    void getUri() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Cache-Control", "max-age=0"));
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.empty(), httpRequestBody);

        assertThat(httpRequest.getUri()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("쿠키 값 검사")
    void findCookie() {
        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /index.html HTTP/1.1");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Collections.singletonMap("Cookie", "srftoken=VLcTfULqEDjd5LMaY4fTDxSYaZUHFnEhWDY4uioygZOLVyzHTIerHILgDiiWf0NO; logined=true"));
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader,
                QueryParameter.empty(), httpRequestBody);
        assertThat(httpRequest.getCookie("logined")).isEqualTo("true");
    }
}