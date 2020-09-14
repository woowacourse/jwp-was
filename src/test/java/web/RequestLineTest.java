package web;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

    @DisplayName("RequestLine 파싱")
    @Test
    void parseRequestLine() throws IOException {
        String request = "GET /favicon.ico HTTP/1.1";
        String[] req = request.split(" ");
        String path = req[1];
        String protocol = req[2];

        BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream(request.getBytes())
            ));

        RequestLine requestLine = new RequestLine(bufferedReader);

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getProtocol()).isEqualTo(protocol);
    }
}
