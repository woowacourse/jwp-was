package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest {

    @Test
    @DisplayName("가입 후 로그인 확인")
    void doPost() throws IOException {
        User user = new User("test", "test", "test", "test@test");
        DataBase.addUser(user);
        String input = "POST /user/login HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 25\r\n" +
                "\r\n" +
                "userId=test&password=test";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        LoginController loginController = new LoginController();

        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        loginController.doPost(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Set-Cookie"), "logined=true;");
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 302 FOUND\r\n");
        DataBase.removeUser(user);
    }

    @Test
    @DisplayName("가입 되지 않은 사용자")
    void notFoundUser() throws IOException {
        String input = "POST /user/login HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 25\r\n" +
                "\r\n" +
                "userId=test&password=test";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        LoginController loginController = new LoginController();
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        loginController.doPost(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Set-Cookie"), "logined=false;");
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 302 FOUND\r\n");
    }
}