package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    @DisplayName("RequestHeader URI 확인")
    @Test
    void getUriTest() throws IOException {
        String request = "GET /js/chunk-vendors.js HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        RequestUri requestUri = new RequestUri(br);

        assertThat(requestUri.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestUri.getRequestPath()).isEqualTo("/js/chunk-vendors.js");
        assertThat(requestUri.getProtocol()).isEqualTo("HTTP/1.1");
    }
}