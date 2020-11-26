package controller;

import db.DataBase;
import java.io.IOException;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpSession;
import webserver.HttpSessions;
import webserver.controller.Controller;

public class ListUserControllerTest extends ControllerTest {

    private final User user = new User("javajigi", "password", "JaeSung", "bingbong@bingbong");

    @BeforeEach
    void setUp() {
        DataBase.addUser(user);
    }

    @DisplayName("LoginedCookie가 True일 때 ListUserController Forward")
    @Test
    void doGet_LoginedCookieIsTrue_Forward() throws IOException {
        HttpSession session = HttpSessions.createHttpSession("session");
        session.setAttribute("user", user);
        String fileName = "Http_Request_GET_SessionCookie_Login_True.txt";
        Controller controller = new ListUserController();

        String body = service(fileName, controller);

        Assertions.assertThat(body).contains("HTTP/1.1 200 Ok");
    }

    @DisplayName("LoginedCookie가 False일 때 ListUserController Redirect")
    @Test
    void doGet_LoginCookieIsFalse_Redirect() throws IOException {
        String fileName = "Http_Request_GET_SessionCookie_Login_False.txt";
        Controller controller = new ListUserController();

        String body = service(fileName, controller);

        Assertions.assertThat(body).contains("HTTP/1.1 302 Found");
    }
}
