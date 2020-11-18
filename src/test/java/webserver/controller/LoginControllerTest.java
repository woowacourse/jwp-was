package webserver.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.HttpRequestFixture;
import webserver.http.Protocol;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.StatusCode;
import webserver.http.response.StatusLine;

class LoginControllerTest {
    private LoginController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
    }

    @DisplayName("로그인 요청에 대한 회원정보가 존재하고 패스워드 일치하면 로그인 및 페이지 이동한다.")
    @Test
    void post() throws IOException {
        DataBase.addUser(new User("javajigi", "password", "name", "email"));
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfLogin();

        HttpResponse response = controller.service(httpRequest);

        assertThat(response.getStatusLine()).isEqualToComparingFieldByField(
            new StatusLine(Protocol.ONE_POINT_ONE, StatusCode.FOUND));
        assertThat(response.getHeader()).isEqualTo(String.format("Location: /index.html%n"));
    }

    @DisplayName("로그인 요청한 아이디가 존재하지 않는 경우 로그인 실패 페이지로 이동한다.")
    @Test
    void post_shouldRedirectToLoginFailPage_whenUserIdDoesNotExist() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfLogin();

        HttpResponse response = controller.service(httpRequest);

        assertThat(response.getStatusLine()).isEqualToComparingFieldByField(
            new StatusLine(Protocol.ONE_POINT_ONE, StatusCode.FOUND));
        assertThat(response.getHeader()).isEqualTo(String.format("Location: /user/login_failed.html%n"));
    }

    @DisplayName("로그인 요청한 아이디의 패스워드가 일치하지 않으면 로그인 실패 페이지로 이동한다.")
    @Test
    void post_shouldRedirectToLoginFailPage_whenPasswordIsWrong() throws IOException {
        DataBase.addUser(new User("javajigi", "another", "name", "email"));
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfLogin();

        HttpResponse response = controller.service(httpRequest);

        assertThat(response.getStatusLine()).isEqualToComparingFieldByField(
            new StatusLine(Protocol.ONE_POINT_ONE, StatusCode.FOUND));
        assertThat(response.getHeader()).isEqualTo(String.format("Location: /user/login_failed.html%n"));
    }
}