package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestMappingTest {

    @DisplayName("GET 매핑 일치 여부 체크")
    @Test
    void match_GET() throws IOException {
        String request = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n"
            + "\n";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));
        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);

        HttpRequest expected = new HttpRequest(requestLine, requestHeader, null);

        assertAll(
            () -> assertThat(HttpRequestMapping.GET("/index.html").match(expected)).isTrue(),
            () -> assertThat(HttpRequestMapping.POST("/index.html").match(expected)).isFalse()
        );

    }

    @DisplayName("POST 매핑 일치 여부 체크")
    @Test
    void match_POST() throws IOException {
        String request = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 71\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=sonypark&password=sony123&name=sony&email=sonypark0204@gmail.com";

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())));
        RequestLine requestLine = new RequestLine(bufferedReader);
        RequestHeader requestHeader = new RequestHeader(bufferedReader);
        RequestBody requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());

        HttpRequest expected = new HttpRequest(requestLine, requestHeader, requestBody);

        assertAll(
            () -> assertThat(HttpRequestMapping.POST("/user/create").match(expected)).isTrue(),
            () -> assertThat(HttpRequestMapping.GET("/user/create").match(expected)).isFalse()
        );
    }

}
