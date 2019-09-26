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
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserListControllerTest {

    @Test
    @DisplayName("로그인 상태에서 유저리스트 출력")
    void doGet() throws IOException {
        User user = new User("testUser", "test", "test", "test");
        DataBase.addUser(user);
        String input = "POST /user/login HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 29\r\n" +
                "\r\n" +
                "userId=testUser&password=test";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        LoginController loginController = new LoginController();

        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        loginController.doPost(httpRequest, httpResponse);

        UserListController userListController = new UserListController();

        input = "GET /user/list HTTP/1.1\r\n" +
                "Content-Type: text/html\r\n" +
                "Cookie: logined=true; \r\n" +
                "\r\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        httpRequest = RequestParser.parse(inputStream);
        httpResponse = new HttpResponse();
        userListController.doGet(httpRequest, httpResponse);

        byte[] body = httpResponse.getHttpResponseBody().getBody();
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 200 OK\r\n");
        assertTrue(new String(body).contains("testUser"));

        DataBase.removeUser(user);
    }
}