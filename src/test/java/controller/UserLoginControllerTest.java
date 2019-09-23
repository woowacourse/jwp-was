package controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import webserver.RequestDispatcher;
import webserver.RequestParser;
import webserver.Response;
import webserver.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @BeforeAll
    static void init() throws IOException {
        RequestDispatcher.handle(RequestParser.parse(new FileInputStream(new File(TEST_DIRECTORY + "/PostSignUp.txt"))));
    }

    @Test
    void login() throws IOException {

        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostLogin.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/index.html");
        assertThat(res.getCookie("logined")).isEqualTo("true");
    }

    @Test
    void login_failed() throws IOException {
        RequestDispatcher.handle(RequestParser.parse(new FileInputStream(new File(TEST_DIRECTORY + "/PostSignUp.txt"))));

        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostLoginFailed.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(res.getCookie("logined")).isEqualTo("false");
    }
}