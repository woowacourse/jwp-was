package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.view.View;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserLoginControllerTest {
    private User user;
    private static final Logger log = LoggerFactory.getLogger(UserLoginControllerTest.class);


    @BeforeAll
    void setup() {
        user = new User("javajigi", "password", "name", "email");
        DataBase.addUser(user);
        log.info("hello");
    }

    @Test
    void 로그인_성공() throws IOException {
        String requestSting = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 33\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password";

        HttpResponse response = createResponse(requestSting);
        assertThat(response.getHeader().contains("/index.html")).isTrue();
    }

    @Test
    void 로그인_실패() throws IOException {
        String requestSting = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 33\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=fail&password=password";

        HttpResponse response = createResponse(requestSting);


        assertThat(response.getHeader().contains("/login_failed.html")).isTrue();


    }

    private HttpResponse createResponse(String requestSting) throws IOException {
        UserLoginController controller = new UserLoginController();
        InputStream in = new ByteArrayInputStream(requestSting.getBytes());
        HttpRequest request = new HttpRequest(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);
        View view = controller.service(request, response);
        ResponseResolver.resolve(view, response);

        return response;
    }

}