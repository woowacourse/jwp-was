package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {
    private Request request;

    @BeforeEach
    void setUp() {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1");
        RequestHeader requestHeader = new RequestHeader(
                "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 59\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*");
        RequestBody requestBody = new RequestBody(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n");

        request = new Request(requestLine, requestHeader, requestBody);
    }

    @Test
    @DisplayName("Header에서 html URL 추출")
    public void extractUrl() {
        assertThat(request.extractUrl()).isEqualTo("/user/create");
    }

    @Test
    public void isPostMethod() {
        assertThat(request.isPostMethod()).isTrue();
    }

    @Test
    public void extractHttpVersion() {
        assertThat(request.extractHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    public void extractHeader() {
        assertThat(request.extractHeader("Host")).isEqualTo("localhost:8080");
    }
}