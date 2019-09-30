package domain.controller;

import domain.db.UserDataBase;
import domain.model.User;
import mvc.config.ControllerScanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.HttpTestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SignupControllerTest {
    private HttpTestClient client;

    @BeforeEach
    void setUp() throws IOException {
        ControllerScanner.scan();
        client = new HttpTestClient("localhost", 8080);
    }

    @Test
    void get() {
        String request = "GET /signup HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        String response = client.send(request);
        assertThat(response).contains("이름");
    }

    @Test
    void post() {
        String request = "POST /signup HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 79\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=moomin&password=1234&name=%EB%AC%B4%EB%AF%BC&email=moomin%40woowahan.com";
        String response = client.send(request);
        assertThat(response).contains("Location: /");
        User user = UserDataBase.findUserById("moomin");
        assertThat(user.getEmail()).isEqualTo("moomin@woowahan.com");
        assertThat(user.getName()).isEqualTo("무민");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getUserId()).isEqualTo("moomin");
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}