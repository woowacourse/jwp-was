package application.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import request.HttpRequest;
import response.HttpResponse;
import session.Session;
import session.SessionStorage;

@DisplayName("UserController - 회원가입")
class UserControllerCreateTest {

    private UserController userController = new UserController();

    @Test
    @DisplayName("happy case")
    void serviceForJoin() {
        HttpRequest joinRequest = new HttpRequest("POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=tls123&password=passwordoftls&name=신&email=test@email.com");
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(joinRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 302 Found")).isTrue();
        assertThat(responseHeader.contains("Location: /")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"userId=", "password=", "nickname=", "email=", "zzzzzzz", "", " "})
    @DisplayName("요청 body가 잘못된 경우")
    void serviceForJoin_IfBodyIsWrong(String wrongBody) {
        HttpRequest joinRequest = new HttpRequest("POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            wrongBody);
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(joinRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 400 Bad Request")).isTrue();
    }
}
