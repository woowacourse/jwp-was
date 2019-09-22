package http;

import http.request.Request;
import http.request.RequestBody;
import http.request.RequestHeader;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    @DisplayName("Header에서 html URL 추출")
    public void extractUrl() {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1\n");
        RequestHeader requestHeader = new RequestHeader(
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*");
        RequestBody requestBody = new RequestBody(
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n");

        Request request = new Request(requestLine, requestHeader, requestBody);
        assertThat(request.extractUrl()).isEqualTo("/user/create");
    }
}