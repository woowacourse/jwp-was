package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    @DisplayName("HTTP GET 요청")
    public void httpGetRequest() {
        List<String> inputs = Arrays.asList(
                "GET /user/create?userId=javajigi&password=password HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);

        HttpRequest httpRequest = new HttpRequest(httpRequestHeader);
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("HTTP POST 요청")
    public void httpPostRequest() {
        List<String> inputs = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 46",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");
        String body = "userId=javajigi&password=password";

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);
        HttpRequestBody httpRequestBody = new HttpRequestBody(body);
        HttpRequest httpRequest = new HttpRequest(httpRequestHeader, httpRequestBody);

        assertThat(httpRequest.getMethod()).isEqualTo("POST");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("46");
        assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("루트 URL에 대하여 index.html 반환")
    public void httpGetRequestIfRootUri() {
        List<String> inputs = Arrays.asList(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive");
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);

        HttpRequest httpRequest = new HttpRequest(httpRequestHeader);
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getResourcePath()).isEqualTo("/index.html");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
    }
}