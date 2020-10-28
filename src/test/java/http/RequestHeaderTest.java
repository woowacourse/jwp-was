package http;

import http.request.RequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestHeaderTest {
    @Test
    @DisplayName("헤더를 잘 파싱하는지 테스트")
    void headerParseTest() throws IOException {
        String request = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestHeader requestHeader = new RequestHeader(br);

        assertEquals(93, requestHeader.getContentLength());
    }

    @Test
    @DisplayName("[예외] 헤더 값이 누락됐을 때 테스트")
    void invalidHeaderParseTest() {
        String request = "Host: \n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        assertThatThrownBy(() -> new RequestHeader(br))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Host");
    }
}