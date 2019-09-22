package support;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpTestClientTest {
    private HttpTestClient client;

    @BeforeEach
    void setUp() throws IOException {
        client = new HttpTestClient("localhost", 8080);
    }

    @Test
    void send() {
        String plainTextRequest = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        String response = client.send(plainTextRequest);
        System.out.println(response);
        assertThat(response).contains("로그인");
    }
}
