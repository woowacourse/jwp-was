package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private static LoginController loginController;

    @BeforeAll
    static void setUpBeforeAll() {
        loginController = new LoginController();
        User user = new User("loginTest", "password", "Test Name", "login@login.test");

        DataBase.addUser(user);
    }

    @Test
    @DisplayName("password가 일치하는 경우 response의 set-Cookie logined값을 true로 설정한다.")
    void setCookie_ifUserPassword_isCorrect() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Success.txt");

        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @Test
    @DisplayName("password가 일치하는 경우 index 페이지로 redirect한다.")
    void redirectToIndex_ifUserPassword_isCorrect() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Success.txt");

        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    @DisplayName("password가 틀린 경우 response의 set-Cookie logined값을 flase로 설정한다.")
    void setCookie_ifUserPassword_isWrong() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Wrong_Password.txt");

        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("password가 틀린 경우 login fail 페이지로 리다이렉트한다.")
    void redirectToLoginFailPage_ifUserPassword_isWrong() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Wrong_Password.txt");

        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    @DisplayName("유저 id가 없는 경우 response의 set-Cookie logined값을 flase로 설정한다.")
    void setCookie_ifUserId_isWrong() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Wrong_Id.txt");

        assertThat(response.getHeader("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("유저 id가 없는 경우 login fail 페이지로 리다이렉트한다.")
    void redirectToLoginFailPage_ifUserId_doesNotExist() throws IOException {
        HttpResponse response = handleLoginRequest("Http_Post_Login_Wrong_Id.txt");

        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
    }

    private HttpResponse handleLoginRequest(String HttpRequestMessage) throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest(HttpRequestMessage);
        HttpResponse response = HttpResponse.of(request.getVersion());
        loginController.doPost(request, response);

        return response;
    }
}