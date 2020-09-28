package webserver.requestmapping.behavior;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.DataBase;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;

class UserCreateBehaviorTest {

    private BufferedReader REQUEST_BUFFERED_READER;

    private static final String REQUEST =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
        DataBase.deleteAll();
    }

    @Test
    void behave() {
        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER);
        ResponseEntity responseEntity = ResponseEntity.empty();

        UserCreateBehavior userCreateBehavior = new UserCreateBehavior();
        userCreateBehavior.behave(requestEntity, responseEntity);

        User expectedUser = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
            () -> assertThat(responseEntity.getHttpHeader().findOrEmpty("Location")).isEqualTo("/index.html"),
            () -> assertThat(DataBase.findAll()).hasSize(1),
            () -> assertThat(DataBase.findUserById("javajigi")).usingRecursiveComparison().isEqualTo(expectedUser)
        );
    }
}