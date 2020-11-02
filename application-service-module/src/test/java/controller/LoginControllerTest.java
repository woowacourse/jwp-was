package controller;

import http.SessionContainer;
import http.request.Cookie;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoginControllerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final SessionContainer sessionContainer = SessionContainer.getInstance();

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("ordincode", "1234", "윤성학", "email@email.com"));
    }

    @DisplayName("로그인 성공시 응답 테스트")
    @Test
    void responseWhenLoginSuccess() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "ordincode");
        params.put("password", "1234");

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put(SessionContainer.SESSION_KEY_FOR_COOKIE, sessionContainer.createSession());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, ""),
                new RequestHeader(new HashMap<>()),
                new RequestParams(params),
                new Cookie(cookies)
        );
        LoginController loginController = new LoginController();
        loginController.doPost(httpRequest, new HttpResponse(outputStream));

        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 302 FOUND"),
                () -> assertThat(outputStream.toString()).contains("Location: /index.html")
        );
    }

    @DisplayName("로그인 실패시 응답 테스트 - 비밀먼호가 다른 경우")
    @Test
    void responseWhenLoginFailBecauseWrongPassword() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "ordincode");
        params.put("password", "wrongPassword");

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put(SessionContainer.SESSION_KEY_FOR_COOKIE, sessionContainer.createSession());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, ""),
                new RequestHeader(new HashMap<>()),
                new RequestParams(params),
                new Cookie(cookies)
        );
        LoginController loginController = new LoginController();
        loginController.doPost(httpRequest, new HttpResponse(outputStream));

        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 302 FOUND"),
                () -> assertThat(outputStream.toString()).contains("Location: /user/login_failed.html")
        );
    }

    @DisplayName("로그인 실패시 응답 테스트 - 존재하지 않는 아이디일 경우")
    @Test
    void responseWhenLoginFailBecauseNotFoundUser() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "notFoundUserId");
        params.put("password", "1234");

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put(SessionContainer.SESSION_KEY_FOR_COOKIE, sessionContainer.createSession());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, ""),
                new RequestHeader(new HashMap<>()),
                new RequestParams(params),
                new Cookie(cookies)
        );
        LoginController loginController = new LoginController();
        loginController.doPost(httpRequest, new HttpResponse(outputStream));

        System.out.println(outputStream.toString());
        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 302 FOUND"),
                () -> assertThat(outputStream.toString()).contains("Location: /user/login_failed.html")
        );
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }
}
