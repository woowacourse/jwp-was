package model;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {

    @Test
    void getUrl() throws IOException {
        String method = "GET /index.html HTTP/1.1";
        InputStream in = new ByteArrayInputStream(method.getBytes());
        Request request = new Request(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getUrl()).isEqualTo("/index.html");
    }
}