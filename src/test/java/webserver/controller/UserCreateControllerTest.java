package webserver.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.HttpRequestFixture;
import webserver.domain.request.HttpRequest;

class UserCreateControllerTest {
    @DisplayName("회원가입에 대한 GET 요청이 들어오면 파라미터의 값으로 회원을 생성한다.")
    @Test
    void get() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();

        Controller controller = new UserCreateController();
        controller.service(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("회원가입에 대한 POST 요청이 들어오면 바디의 값으로 회원을 생성한다.")
    @Test
    void post() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfPostMethod();

        Controller controller = new UserCreateController();
        controller.service(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }
}