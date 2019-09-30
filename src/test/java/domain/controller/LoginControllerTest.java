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

class LoginControllerTest {
    private HttpTestClient client;

    @BeforeEach
    void setUp() throws IOException {
        ControllerScanner.scan();
        client = new HttpTestClient("localhost", 8080);
    }

    @Test
    void get() {
        String request = "GET /user/login HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        String response = client.send(request);
        assertThat(response).contains("사용자 아이디");
    }

    @Test
    void post() {
        UserDataBase.addUser(new User(
                "moomin",
                "1234",
                "무민",
                "moomin@woowahan.com"
        ));
        String request = "POST /user/login HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 27\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=moomin&password=1234";
        String response = client.send(request);
        assertThat(response).contains("Location: /");
        UserDataBase.deleteUser("moomin");
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}