package controller;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import java.io.IOException;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.Controller;

public class LoginControllerTest extends ControllerTest {

    private final User user = new User("javajigi", "password", "JaeSung", "bingbong@bingbong");

    @BeforeEach
    void setUp() {
        DataBase.addUser(user);
    }

    @DisplayName("LoginController doPost Login 후 세션 쿠키 추가")
    @Test
    void doPost_Login_LoginedCookieIsTrue() throws IOException {
        String fileName = "Http_Request_POST_Login.txt";
        Controller controller = new LoginController();

        String body = service(fileName, controller);

        Assertions.assertAll(
            () -> assertThat(body).contains("HTTP/1.1 302 Found"),
            () -> assertThat(body)
                .contains("Set-Cookie: JSESSIONID="),
            () -> assertThat(body)
                .doesNotContain("아이디 또는 비밀번호가 틀립니다.")
        );
    }

    @DisplayName("LoginController doPost Login Failed 후 리다이렉트")
    @Test
    void doPost_LoginFailed_LoginedCookieIsFalse() throws IOException {
        String fileName = "Http_Request_POST_Login_Failed.txt";
        Controller controller = new LoginController();

        String body = service(fileName, controller);

        Assertions.assertAll(
            () -> assertThat(body).contains("HTTP/1.1 302 Found"),
            () -> assertThat(body).contains("아이디 또는 비밀번호가 틀립니다.")
        );
    }
}
