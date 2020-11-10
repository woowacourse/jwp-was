package user.service.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.was.http.request.Request;
import http.was.http.response.Response;
import user.service.common.TestFileIo;
import user.service.db.DataBase;
import user.service.model.User;

class LogoutControllerTest {

    @DisplayName("로그아웃 성공 테스트")
    @Test
    void doDeleteTest() throws Exception {
        DataBase.addUser(new User("javajigi", "1234", "박재성", "pobi@gmail.com"));

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_login_request.txt"));
        Response response = new Response(result);

        LoginController.doPost(request, response);

        LogoutController.doDelete(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /index.html ")
        );

    }

}
