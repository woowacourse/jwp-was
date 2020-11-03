package web.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;

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

        InputStream in = new FileInputStream(new File(testDirectory + "Http_Login.txt"));
        HttpRequest request = new HttpRequest(in);
        HttpResponse response = new HttpResponse(null);

        Controller loginController = new LoginController();
        loginController.service(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders()).containsEntry("Location", "/index.html");
    }

    @DisplayName("로그인 실패")
    @Test
    public void loginFail() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_Login.txt"));
        HttpRequest request = new HttpRequest(in);
        HttpResponse response = new HttpResponse(null);

        Controller loginController = new LoginController();
        loginController.service(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().get("Set-Cookie")).isNull();
        assertThat(response.getHeaders()).containsEntry("Location", "/user/login_failed.html");
    }
}