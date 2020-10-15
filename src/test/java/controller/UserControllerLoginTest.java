package controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import controller.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import response.HttpResponse;

@DisplayName("UserController - 로그인")
class UserControllerLoginTest {

    private static final String TEST_USER_ID = "test";
    private static final String TEST_PASSWORD = "test";

    private UserController userController = new UserController();

    @BeforeEach
    void setUp() {
        HttpRequest joinRequest = new HttpRequest("POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=" + TEST_USER_ID +
                "&password=" + TEST_PASSWORD + "&name=test&email=test@email.com");

        HttpResponse response = userController.service(joinRequest);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /")).isTrue();
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        HttpRequest loginRequest = new HttpRequest("POST /user/login HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=" + TEST_USER_ID + "&password=" + TEST_PASSWORD);

        HttpResponse response = userController.service(loginRequest);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /")).isTrue();
        assertThat(responseHeader.contains("Set-Cookie: login=true")).isTrue();
    }

    @Test
    @DisplayName("로그인 실패 - ID 오류")
    void loginFailed() {
        HttpRequest loginRequest = new HttpRequest("POST /user/login HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=idThatDoesNotExist&password=" + TEST_PASSWORD);

        HttpResponse response = userController.service(loginRequest);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /user/login_failed.html")).isTrue();
        assertThat(responseHeader.contains("Set-Cookie: login=false")).isTrue();
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 틀린 경우")
    void loginFailedByWrongPassword() {
        HttpRequest loginRequest = new HttpRequest("POST /user/login HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=" + TEST_USER_ID + "&password=wrong");

        HttpResponse response = userController.service(loginRequest);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /user/login_failed.html")).isTrue();
        assertThat(responseHeader.contains("Set-Cookie: login=false")).isTrue();
    }
}
