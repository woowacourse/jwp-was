package http;

import http.request.RequestLine;
import http.request.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestLineTest {

    @Test
    @DisplayName("RequestLine을 잘 파싱하는지 테스트")
    void lineParseTest() throws IOException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        RequestLine requestLine = new RequestLine(br);

        assertTrue(requestLine.isPathEqualTo("/user/create"));
        assertTrue(requestLine.isMethodEqualTo(RequestMethod.POST));
        assertTrue(requestLine.isHttpVersionEqualTo("HTTP/1.1"));
    }

    @Test
    @DisplayName("[예외] 잘못된 RequestLine이 입력된 경우 테스트")
    void wrongLineParseTest() {
        String request = "Host: localhost:8080\n";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        assertThatThrownBy(() -> new RequestLine(br)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported");
    }

    @Test
    @DisplayName("[예외] 빈 RequestLine이 입력된 경우 테스트")
    void emptyLineParseTest() {
        String request = "";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        assertThatThrownBy(() -> new RequestLine(br)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empty");
    }
}