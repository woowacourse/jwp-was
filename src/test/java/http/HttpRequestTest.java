package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.request.HttpRequest;

class HttpRequestTest {

    @DisplayName("올바른 입력에 대하여 HttpRequest를 생성한다.")
    @Test
    void createHttpRequest() throws IOException {
        String httpRequestInput = "POST /index.html HTTP/1.1\r\n"
            + "Content-Type: text/html;charset=UTF-8\r\n"
            + "Content-Length: 93\r\n"
            + "Accept-Language: en-US,en;q=0.9\r\n"
            + "Cookie: JSESSIONID=D03222408A13F2797D6DFECB7CFC74EE\r\n"
            + "\r\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n";

        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader reader = new BufferedReader(inputString);

        HttpRequest httpRequest = new HttpRequest(reader);

        assertAll(
            () -> assertThat("/index.html").isEqualTo(httpRequest.getHttpPath()),
            () -> assertThat("93").isEqualTo(httpRequest.getHttpHeaderParameterOf("Content-Length")),
            () -> assertThat("password").isEqualTo(httpRequest.getHttpBodyValueOf("password")),
            () -> assertThat(true).isEqualTo(httpRequest.isStaticFile()),
            () -> assertThat("text/html").isEqualTo(httpRequest.getContentType())

        );
    }
}