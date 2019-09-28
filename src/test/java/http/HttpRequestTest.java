package http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {

    @Test
    void GET_Request_정상_생성() {
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.GET,
                new HttpUri("/index.html"),
                HttpVersion.V_1_1);

        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        HttpHeader header = new HttpHeader(headerLines);
        HttpBody body = new HttpBody("");
        HttpRequest httpRequest = new HttpRequest(requestLine, header, body);

        assertTrue(httpRequest.isStaticRequest());
        assertEquals(httpRequest.getMethod(), HttpMethod.GET);
        assertEquals(httpRequest.getUri(), "/index.html");
    }

    @Test
    void POST_Request_정상_생성_build() {
        HttpRequestLine requestLine = new HttpRequestLine(
                HttpMethod.POST,
                new HttpUri("/user/create"),
                HttpVersion.V_1_1);

        List<String> headerLines = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");

        HttpHeader header = new HttpHeader(headerLines);
        HttpBody body = new HttpBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        HttpRequest.HttpRequestBuilder requestBuilder = new HttpRequest.HttpRequestBuilder();
        HttpRequest httpRequest = requestBuilder
                .requestLine(requestLine)
                .body(body)
                .header(header)
                .build();

        assertFalse(httpRequest.isStaticRequest());
        assertEquals(httpRequest.getMethod(), HttpMethod.POST);
        assertEquals(httpRequest.getUri(), "/user/create");
    }
}
