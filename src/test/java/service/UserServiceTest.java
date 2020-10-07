package service;

import db.DataBase;
import http.HttpRequest;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    @Test
    void createUserTest() throws IOException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        UserService userService = UserService.getInstance();
        userService.createUser(new HttpRequest(br));

        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }

    @Test
    void authenticateUserTest() throws IOException {
        String request = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 34\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        DataBase.addUser(new User("javajigi", "password", "자바지기", "javajigi@slipp.net"));

        UserService userService = UserService.getInstance();
        assertThat(userService.authenticateUser(new HttpRequest(br))).isTrue();
    }

    @Test
    void authenticateUserFailedTest() throws IOException {
        String request = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 34\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        DataBase.addUser(new User("javajigi", "12345678", "자바지기", "javajigi@slipp.net"));

        UserService userService = new UserService();
        assertThat(userService.authenticateUser(new HttpRequest(br))).isFalse();
    }
}