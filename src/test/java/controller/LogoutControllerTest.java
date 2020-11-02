package controller;

import common.TestFileIo;
import db.DataBase;
import http.request.Request;
import http.response.Response;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LogoutControllerTest {

    @DisplayName("로그아웃 성공 테스트")
    @Test
    void doDeleteTest() throws Exception {
        DataBase.addUser(new User("javajigi", "1234", "박재성", "pobi@gmail.com"));

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_login_request.txt"));
        Response response = new Response(result);

        LoginController loginController = new LoginController();
        loginController.doPost(request, response);

        LogoutController logoutController = new LogoutController();
        logoutController.doDelete(request, response);

        String actual = result.toString();

        assertAll(
            () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
            () -> assertThat(actual).contains("Location: /index.html ")
        );

    }

}