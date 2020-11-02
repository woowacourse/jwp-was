package controller;

import common.TestFixtureFactory;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserControllerTest {
    private final UserController userController = new UserController();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @DisplayName("유저 생성 응답 테스트")
    @Test
    void userCreateSuccess() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "ordincdoe");
        params.put("password", "1234");
        params.put("name", "윤성학");
        params.put("email", "email@email.com");

        HttpRequest httpRequest = TestFixtureFactory.makeHttpRequestForControllerTest(params, new HashMap<>());

        userController.doPost(httpRequest, new HttpResponse(outputStream));

        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 302 FOUND"),
                () -> assertThat(outputStream.toString()).contains("Location: /index.html"),
                () -> assertThat(DataBase.findAll().size()).isNotZero()
        );
    }
}
