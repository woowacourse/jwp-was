package application.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import response.HttpResponse;

@DisplayName("UserController - 전체 회원 목록 조회")
class UserControllerFindAllUsersTest extends UserControllerTest {

    private UserController userController = new UserController();

    @Test
    @DisplayName("정상 응답")
    void findAllUsers() {
        HttpRequest findAllUsersRequest = new HttpRequest(
            "GET /user/list HTTP/1.1\n"
            + "Cookie: login=true\n", "");

        HttpResponse response = userController.service(findAllUsersRequest);
        String responseHeader = response.buildHeader();

        // then
        assertThat(responseHeader).startsWith("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그인 안한 사용자가 요청할 경우 로그인 페이지로 리다이렉트")
    void unauthorized() {
        HttpRequest findAllUsersRequest = new HttpRequest("GET /user/list HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n", "");

        HttpResponse response = userController.service(findAllUsersRequest);
        String responseHeader = response.buildHeader();

        assertThat(responseHeader).startsWith("HTTP/1.1 302 Found");
    }
}
