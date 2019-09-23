package controller;

import org.junit.jupiter.api.BeforeAll;
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

public class LoginControllerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources";

    @BeforeAll
    static void init() throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        RequestDispatcher.handle(RequestParser.parse(new FileInputStream(new File(TEST_DIRECTORY + "/PostSignUp.txt"))), httpResponse);
    }

    @Test
    void login() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostLogin.txt"));
        HttpResponse res = new HttpResponse();
        RequestDispatcher.handle(RequestParser.parse(in), res);

        assertThat(res.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/index.html");
        assertThat(res.getCookie("logined")).isEqualTo("true");
    }

    @Test
    void login_failed() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/PostLoginFailed.txt"));
        HttpResponse res = new HttpResponse();
        RequestDispatcher.handle(RequestParser.parse(in), res);

        assertThat(res.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(res.getCookie("logined")).isEqualTo("false");
    }
}
