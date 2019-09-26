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

class UserListControllerTest {
    private String requestForm =
            "POST /user/list HTTP/1.1" + "\r\n" +
                    "Host: localhost:8080" + "\r\n" +
                    "Connection: keep-alive" + "\r\n" +
                    "%s" + "\r\n" +
                    "\r\n";
    ;

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("adien", "1234", "aiden", "aiden@naver.com"));
        DataBase.addUser(new User("roby", "1234", "roby", "roby@naver.com"));
    }

    @Test
    @DisplayName("로그인 성공시 리스트 출력")
    public void loginSuccess() throws Exception {
        String request = String.format(requestForm, "Cookie: logined=true");

        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        AbstractController controller = new UserListController();
        controller.doGet(httpRequest, httpResponse);

        assertThat(new String(httpResponse.getBody())).contains("aiden@naver.com");
        assertThat(new String(httpResponse.getBody())).contains("roby@naver.com");
    }

    @Test
    @DisplayName("로그인 실패시 리다이렉트")
    public void loginFail() throws Exception {
        String request = String.format(requestForm, "Cookie: logined=false");

        InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        AbstractController controller = new UserListController();
        controller.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("Location: /index.html");
    }
}