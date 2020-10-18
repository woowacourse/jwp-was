package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static utils.TestIOUtils.createRequestBufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class ListUserControllerTest {

    @DisplayName("LoginedCookie가 True일 때 ListUserController Forward")
    @Test
    void doGet_LoginedCookieIsTrue_Forward() throws IOException {
        HttpRequest loginRequest = new HttpRequest(createRequestBufferedReader("Http_Request_GET_Cookie_Login_True.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(byteArrayOutputStream);
        Controller controller = new ListUserController();
        controller.service(loginRequest, response);

        String body = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);

        assertAll(() -> assertThat(body).contains("HTTP/1.1 200 Ok"));
    }

    @DisplayName("LoginedCookie가 False일 때 ListUserController Redirect")
    @Test
    void doGet_LoginCookieIsFalse_Redirect() throws IOException {
        HttpRequest loginRequest = new HttpRequest(createRequestBufferedReader("Http_Request_GET_Cookie_Login_False.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(byteArrayOutputStream);
        Controller controller = new ListUserController();
        controller.service(loginRequest, response);

        String body = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);

        assertAll(() -> assertThat(body).contains("HTTP/1.1 302 Found"));
    }
}
