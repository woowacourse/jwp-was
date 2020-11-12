package implementedbehavior;

import db.DataBase;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserListBehaviorTest {

    private BufferedReader REQUEST_BUFFERED_READER;

    private static final String REQUEST =
        "GET /user/list HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
        DataBase.deleteAll();
    }

    @Test
    void behave() {
        DataBase.addUser(new User("javajigi", "password", "자바지기", "javajigi@gmail.com"));
        DataBase.addUser(new User("yuan", "password", "유안", "yuan@gmail.com"));
        DataBase.addUser(new User("bum", "password", "범블비", "bum@gmail.com"));

        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER);
        ResponseEntity responseEntity = ResponseEntity.empty();

        UserListBehavior userListBehavior = new UserListBehavior();
        userListBehavior.behave(requestEntity, responseEntity);

        assertAll(
            () -> assertThat(responseEntity.getHttpStatus()).isEqualTo(HttpStatus.OK),
            () -> assertThat(responseEntity.getHttpBody().getContent()).contains(Arrays.asList("자바지기", "유안", "범블비"))
        );
    }
}