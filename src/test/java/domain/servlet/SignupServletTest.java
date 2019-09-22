package domain.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.HttpTestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SignupServletTest {
    private HttpTestClient client;

    @BeforeEach
    void setUp() throws IOException {
        client = new HttpTestClient("localhost", 8080);
    }

    @Test
    void doGet() {
        String request =
                "GET /signup HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        String response = client.send(request);
        assertThat(response).contains("사용자 아이디");
    }

    @Test
    void doPost() {
        String request =
                "POST /signup HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 93\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n";
        String response = client.send(request);
        assertThat(response).contains("Location: /");
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}