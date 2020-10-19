package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import db.DataBase;
import java.io.IOException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginControllerTest extends ControllerTest {

    private final User user = new User("javajigi", "password", "JaeSung", "bingbong@bingbong");

    @BeforeEach
    void setUp() {
        DataBase.addUser(user);
    }

    @DisplayName("LoginController doPost Login 후 쿠키 조회")
    @Test
    void doPost_Login_LoginedCookieIsTrue() throws IOException {
        String fileName = "Http_Request_POST_Login.txt";
        Controller controller = new LoginController();

        String body = service(fileName, controller);

        assertAll(
            () -> assertThat(body).contains("HTTP/1.1 302 Found"),
            () -> assertThat(body).contains("Set-Cookie: logined=true")
        );
    }

    @DisplayName("LoginController doPost Login Failed 후 쿠키 조회")
    @Test
    void doPost_LoginFailed_LoginedCookieIsFalse() throws IOException {
        String fileName = "Http_Request_POST_Login_Failed.txt";
        Controller controller = new LoginController();

        String body = service(fileName, controller);

        assertAll(
            () -> assertThat(body).contains("HTTP/1.1 302 Found"),
            () -> assertThat(body).contains("Set-Cookie: logined=false")
        );
    }
}
