package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class HttpRequestTest {
    @Test
    void create() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = new HttpRequest(br);

        assertAll(
            () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(httpRequest.getPath()).isEqualTo("/admin/"),
            () -> assertThat(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET)
        );
    }
}
