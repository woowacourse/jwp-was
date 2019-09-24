package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest {
    @Test
    void user_create_요청() throws IOException {
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        HttpResponse response = createResponse(request);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);
    }

    private HttpResponse createResponse(String requestSting) throws IOException {
        UserCreateController controller = new UserCreateController();
        InputStream in = new ByteArrayInputStream(requestSting.getBytes());
        HttpRequest request = new HttpRequest(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);
        controller.service(request, response);

        return response;
    }
}