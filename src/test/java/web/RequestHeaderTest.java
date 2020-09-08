package web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {

    @DisplayName("RequestHeader URI 확인")
    @Test
    void requestHeaderTest1() throws IOException {
        String request = "GET /js/chunk-vendors.js HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestHeader requestHeader = new RequestHeader(br);

        assertThat(requestHeader.getMethod()).isEqualTo("GET");
        assertThat(requestHeader.getPath()).isEqualTo("/js/chunk-vendors.js");
        assertThat(requestHeader.getProtocol()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("RequestHeader headers 확인")
    @Test
    void requestHeaderTest2() throws IOException {
        String request = "GET /js/chunk-vendors.js HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestHeader requestHeader = new RequestHeader(br);

        Map<String, String> headers = requestHeader.getHeaders();

        assertThat(headers.get("Host")).isEqualTo("localhost:8080");
        assertThat(headers.get("Connection")).isEqualTo("keep-alive");
    }
}