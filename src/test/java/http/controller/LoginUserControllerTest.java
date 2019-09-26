package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LoginUserControllerTest {
    private String requestForm =
                    "POST /user/login HTTP/1.1" + "\r\n" +
                    "Host: localhost:8080" + "\r\n" +
                    "Connection: keep-alive" + "\r\n" +
                    "\r\n" + "%s"
            ;

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("adien", "1234", "aiden", "aiden@naver.com"));
    }

    @Test
    @DisplayName("로그인 실패")
    public void loginFail() throws Exception {
        String request = String.format(requestForm, "userId=pobi&password=1234");

        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        LoginUserController loginUserController = new LoginUserController();
        loginUserController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("Location: /user/login_failed.html");
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() throws Exception {
        String request = String.format(requestForm, "userId=aiden&password=1234");

        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        LoginUserController loginUserController = new LoginUserController();
        loginUserController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("Location: /user/login_failed.html");
    }
}