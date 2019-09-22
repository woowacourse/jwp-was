package domain.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.HttpTestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class IndexServletTest {
    private HttpTestClient client;

    @BeforeEach
    void setUp() throws IOException {
        client = new HttpTestClient("localhost", 8080);
    }

    @Test
    void get() {
        String request =
                "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        String response = client.send(request);
        assertThat(response).contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }
}