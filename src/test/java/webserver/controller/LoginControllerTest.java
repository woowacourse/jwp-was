package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestIOUtils.createRequestBufferedReader;

import db.DataBase;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.response.Cookie;
import webserver.response.HttpResponse;

public class LoginControllerTest {

    private final User user = new User("javajigi", "password", "JaeSung", "bingbong@bingbong");

    @BeforeEach
    void setUp() {
        DataBase.addUser(user);
    }

    @DisplayName("LoginController doPost Login 후 쿠키 조회")
    @Test
    void doPost_Login_LoginedCookieIsTrue() throws IOException {
        HttpRequest loginRequest = new HttpRequest(
            createRequestBufferedReader("Http_Request_POST_Login.txt"));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());
        Controller controller = new LoginController();
        controller.service(loginRequest, response);

        List<Cookie> cookies = response.getCookies();
        Cookie cookie = cookies.get(0);
        assertThat(cookie.getName()).isEqualTo("logined");
        assertThat(cookie.getValue()).isEqualTo("true");
    }

    @DisplayName("LoginController doPost Login Failed 후 쿠키 조회")
    @Test
    void doPost_LoginFailed_LoginedCookieIsFalse() throws IOException {
        HttpRequest loginRequest = new HttpRequest(
            createRequestBufferedReader("Http_Request_POST_Login_Failed.txt"));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());
        Controller controller = new LoginController();
        controller.service(loginRequest, response);

        List<Cookie> cookies = response.getCookies();
        Cookie cookie = cookies.get(0);
        assertThat(cookie.getName()).isEqualTo("logined");
        assertThat(cookie.getValue()).isEqualTo("false");
    }
}
