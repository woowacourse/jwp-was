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

@DisplayName("UserController - 이상한 사용")
class UserControllerIllegalUsageTest extends UserControllerTest {

    private UserController userController = new UserController();

    @Test
    @DisplayName("이상한 uri 로 요청 테스트")
    void serviceForStrangeUri() {
        HttpRequest joinRequest = new HttpRequest("POST /join HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=");
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(joinRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 404 NotFound")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "PUT", "DELETE"})
    @DisplayName("POST 이외의 method 로 회원가입 요청시 404 응답")
    void joinServiceWithHttpMethodNotPost(String httpMethod) {
        HttpRequest joinRequest = new HttpRequest(httpMethod
            + " /join HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n",
            "userId=");
        Session session = SessionStorage.createSession();

        HttpResponse response = userController.service(joinRequest, session);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader.startsWith("HTTP/1.1 404 NotFound")).isTrue();
    }
}
