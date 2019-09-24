package controller;

import org.junit.jupiter.api.Test;
import webserver.HttpResponse;
import webserver.HttpStatus;
import webserver.RequestDispatcher;
import webserver.RequestParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @Test
    void loggedIn() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserList.txt"));
        HttpResponse res = new HttpResponse();
        new UserListController().service(RequestParser.parse(in), res);

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(new String(res.getBody())).contains("이메일");
    }

    @Test
    void loggedIn_failed() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserListFailed.txt"));
        HttpResponse res = new HttpResponse();
        new UserListController().service(RequestParser.parse(in), res);

        assertThat(res.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/user/login.html");
    }
}
