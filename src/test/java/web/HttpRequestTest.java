package web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws IOException {
        String httpRequestInput = "POST /index.html HTTP/1.1\r\n"
                + "Content-Type: text/html;charset=UTF-8\r\n"
                + "Content-Length: 93\r\n"
                + "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n"
                + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n";

        Reader inputString = new StringReader(httpRequestInput);
        final BufferedReader br = new BufferedReader(inputString);

        this.httpRequest = new HttpRequest(br);
    }

    @DisplayName("전달받은 정보로 HttpRequest를 생성한다.")
    @Test
    void httpRequestConstructor() throws IOException {
        String httpRequestInput = "POST /index.html HTTP/1.1\r\n"
                + "Content-Type: text/html;charset=UTF-8\r\n"
                + "Content-Length: 93\r\n"
                + "Accept-Language: en-US,en;q=0.9\r\n"
                + "\r\n"
                + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n";

        Reader inputString = new StringReader(httpRequestInput);
        final BufferedReader br = new BufferedReader(inputString);

        HttpRequest httpRequest = new HttpRequest(br);

        assertThat(httpRequest.getRequestLine()).isEqualTo(new RequestLine("POST /index.html HTTP/1.1"));
        assertThat(httpRequest.getRequestHeader()).isEqualTo(new RequestHeader(Arrays.asList(
                "Content-Type: text/html;charset=UTF-8", "Content-Length: 93", "Accept-Language: en-US,en;q=0.9")));
        assertThat(httpRequest.getRequestBody()).isEqualTo(new RequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"));
    }

    @DisplayName("HttpRequest에서 Method를 추출할 수 있다.")
    @Test
    void getMethod() {
        final String method = this.httpRequest.getMethod();
        assertThat(method).isEqualTo("POST");
    }

    @DisplayName("HttpRequest에서 Path를 추출할 수 있다.")
    @Test
    void getPath() {
        final String method = this.httpRequest.getPath();
        assertThat(method).isEqualTo("/index.html");
    }

    @DisplayName("HttpRequest에서 ContentLength를 추출할 수 있다.")
    @Test
    void getContentLength() {
        final int length = this.httpRequest.getContentLength();
        assertThat(length).isEqualTo(93);
    }
}