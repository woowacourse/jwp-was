package web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    public static final String NEW_LINE = System.lineSeparator();

    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws IOException {
        String httpRequestInput = "POST /index.html HTTP/1.1" + NEW_LINE
                + "Content-Type: text/html;charset=UTF-8" + NEW_LINE
                + "Content-Length: 93" + NEW_LINE
                + "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + NEW_LINE
                + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net" + NEW_LINE;

        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        this.httpRequest = new HttpRequest(br);
    }

    @DisplayName("HttpRequest에서 Method를 추출할 수 있다.")
    @Test
    void getMethod() {
        String method = this.httpRequest.getMethod();
        assertThat(method).isEqualTo("POST");
    }

    @DisplayName("HttpRequest에서 Path를 추출할 수 있다.")
    @Test
    void getPath() {
        String method = this.httpRequest.getPath();
        assertThat(method).isEqualTo("/index.html");
    }

    @DisplayName("HttpRequest에서 ContentLength를 추출할 수 있다.")
    @Test
    void getContentLength() {
        int length = this.httpRequest.getContentLength();
        assertThat(length).isEqualTo(93);
    }
}