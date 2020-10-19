package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListUserControllerTest extends ControllerTest {

    @DisplayName("LoginedCookie가 True일 때 ListUserController Forward")
    @Test
    void doGet_LoginedCookieIsTrue_Forward() throws IOException {
        String fileName = "Http_Request_GET_Cookie_Login_True.txt";
        Controller controller = new ListUserController();

        String body = service(fileName, controller);

        assertThat(body).contains("HTTP/1.1 200 Ok");
    }

    @DisplayName("LoginedCookie가 False일 때 ListUserController Redirect")
    @Test
    void doGet_LoginCookieIsFalse_Redirect() throws IOException {
        String fileName = "Http_Request_GET_Cookie_Login_False.txt";
        Controller controller = new ListUserController();

        String body = service(fileName, controller);

        assertThat(body).contains("HTTP/1.1 302 Found");
    }
}
