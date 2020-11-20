package application.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import response.HttpResponse;
import session.Session;
import session.SessionStorage;

@DisplayName("UserController - 로그인")
class UserControllerLoginTest extends UserControllerTest {

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
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(joinRequest, session);
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

        Session session = SessionStorage.createSession();
        HttpResponse response = userController.service(loginRequest, session);

        assertThat(session.getAttribute("login")).isEqualTo(true);

        String responseHeader = response.buildHeader();
        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /")).isTrue();
        assertThat(responseHeader.contains("sessionId=" + session.getId())).isTrue();
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
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(loginRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /user/login_failed.html")).isTrue();
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
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(loginRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /user/login_failed.html")).isTrue();
    }
}
