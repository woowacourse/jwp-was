package user.service.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.was.http.request.Request;
import http.was.http.response.Response;
import user.service.common.TestFileIo;
import user.service.db.DataBase;
import user.service.model.User;

class LoginControllerTest {

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    void doPostTest() throws Exception {
        addUser();

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_login_request.txt"));
        Response response = new Response(result);

        LoginController.doPost(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /index.html ")
        );
    }

    @DisplayName("회원이 존재하지 않을 때 로그인 실패 테스트")
    @Test
    void doPostTest2() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_login_request.txt"));
        Response response = new Response(result);

        LoginController.doPost(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /user/login_failed.html ")
        );
    }

    @DisplayName("비밀번호가 일치하지 않을 때 로그인 실패 테스트")
    @Test
    void doPostTest3() throws Exception {
        addUser();

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_invalid_login_request.txt"));
        Response response = new Response(result);

        LoginController.doPost(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /user/login_failed.html ")
        );
    }

    private void addUser() {
        DataBase.addUser(new User("javajigi", "1234", "박재성", "pobi@gmail.com"));
    }
}
