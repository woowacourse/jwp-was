package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserControllerTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("User 생성 Post 요청")
    void doPost() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        CreateUserController createUserController = new CreateUserController();
        createUserController.service(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 302 FOUND\r\n");
        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Location"), "/index.html");
    }

    @Test
    @DisplayName("허용하지 않는 메서드")
    void get() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        CreateUserController createUserController = new CreateUserController();
        createUserController.service(httpRequest, httpResponse);

        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 405 Method Not Allowed\r\n");
    }

    @Test
    @DisplayName("유저 중복 가입 테스트")
    void duplicatedUser() throws IOException {
        String input = "POST /user/create HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 45\r\n" +
                "\r\n" +
                "userId=bb&password=bb&name=bb&email=bb@bb.com";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        CreateUserController createUserController = new CreateUserController();
        createUserController.service(httpRequest, httpResponse);

        assertThrows(IllegalArgumentException.class, () -> createUserController.service(httpRequest, httpResponse));
    }

    @Test
    @DisplayName("가입 후 유저 아이디로 검색")
    void findByUserId() throws IOException {
        String input = "POST /user/create HTTP/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 45\r\n" +
                "\r\n" +
                "userId=cc&password=cc&name=cc&email=cc@cc.com";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        CreateUserController createUserController = new CreateUserController();
        createUserController.service(httpRequest, httpResponse);

        assertEquals(DataBase.findUserById("cc"), new User("cc", "cc", "cc", "cc@cc.com"));
    }
}