package web.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;
import webserver.RequestMapping;

class LoginControllerTest {

    private String testDirectory = "./src/test/resources/";

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인")
    @Test
    public void login() throws IOException {
        DataBase.addUser(new User("javajigi", "javable", "pobi", "test@test.com"));

        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST_LOGIN.txt"));
        HttpRequest request = HttpRequest.from(in);
        HttpResponse response = new HttpResponse(createOutputStream("LOGIN_USER.txt"));

        Controller controller = RequestMapping.getController("/user/login");
        controller.service(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Set-Cookie")).isNotNull();
        assertThat(response.getHeaders()).containsEntry("Location", "/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    @DisplayName("로그인 실패")
    @Test
    public void loginFail() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST_LOGIN.txt"));
        HttpRequest request = HttpRequest.from(in);
        HttpResponse response = new HttpResponse(createOutputStream("LOGIN_USER.txt"));

        Controller controller = RequestMapping.getController("/user/login");
        controller.service(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Set-Cookie")).isNull();
        assertThat(response.getHeaders()).containsEntry("Location", "/user/login_failed.html");
    }
}