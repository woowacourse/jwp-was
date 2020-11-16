package implementedbehavior;

import db.DataBase;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserLoginBehaviorTest {

    private BufferedReader REQUEST_BUFFERED_READER;

    private static final String REQUEST =
        "POST /user/login HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 33\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password";

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
        DataBase.deleteAll();
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }

    @Test
    void loginFail() {
        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER);
        ResponseEntity responseEntity = ResponseEntity.empty();

        UserLoginBehavior userLoginBehavior = new UserLoginBehavior();
        userLoginBehavior.behave(requestEntity, responseEntity);

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(responseEntity.getHttpHeader().findOrEmpty("Location")).isEqualTo("/user/login_failed.html")
        );
    }

    @Test
    void loginSuccess() {
        DataBase.addUser(new User("javajigi", "password", "포비", "sss@email.com"));

        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER);
        ResponseEntity responseEntity = ResponseEntity.empty();

        UserLoginBehavior userLoginBehavior = new UserLoginBehavior();
        userLoginBehavior.behave(requestEntity, responseEntity);

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(responseEntity.getHttpHeader().findOrEmpty("Location")).isEqualTo("/index.html"),
            () -> assertThat(responseEntity.getHttpHeader().findOrEmpty("Set-Cookie")).contains("jsessionid=")
        );
    }
}