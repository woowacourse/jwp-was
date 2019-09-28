package servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.HttpTestClient;
import webserver.http.HttpHeaders;
import webserver.http.response.HttpStatus;

public class LoginServletTest {
    private HttpTestClient httpTestClient = new HttpTestClient(HttpTestClient.DEFAULT_PORT);
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("javajigi", "password", "javajigi", "javajigi@0slipp.net");
        DataBase.addUser(user);
    }

    @Test
    void 로그인_성공() {
        httpTestClient.post().uri("/user/login")
                .body("userId=javajigi&password=password")
                .exchange()
                .matchHttpStatus(HttpStatus.FOUND)
                .matchHeader(HttpHeaders.LOCATION, "/");
    }

    @Test
    void 로그인_실패() {
        httpTestClient.post().uri("/user/login")
                .body("userId=javajigi&password=fail")
                .exchange()
                .matchHttpStatus(HttpStatus.OK);
    }


}
