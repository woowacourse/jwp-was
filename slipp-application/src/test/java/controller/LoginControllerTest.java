package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dto.JoinRequestDto;
import http.HttpSession;
import http.HttpStatus;
import http.request.HttpRequest;
import http.request.SimpleHttpRequest;
import http.response.HttpResponse;
import service.UserService;
import servlet.HttpServlet;

class LoginControllerTest {
    private String testDirectory = "./src/test/resources/";
    private UserService userService;
    private HttpServlet loginController;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        loginController = new LoginController(userService);
    }

    @Test
    void login() throws IOException {
        JoinRequestDto joinRequestDto = new JoinRequestDto("javajigi", "password", "pobi", "pobi@slipp.com");
        userService.join(joinRequestDto);

        HttpRequest request = getHttpRequest("HTTP_LOGIN.txt");
        HttpResponse response = new HttpResponse();

        loginController.service(request, response);

        HttpSession session = request.getSession();
        assertAll(
            () -> Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.MOVED_PERMANENTLY),
            () -> Assertions.assertThat(session.getAttribute("logined")).isEqualTo("true")
        );
    }

    @Test
    void login_fail() throws IOException {
        JoinRequestDto joinRequestDto = new JoinRequestDto("javajigi", "passold", "pobi", "pobi@slipp.com");
        userService.join(joinRequestDto);

        HttpRequest request = getHttpRequest("HTTP_LOGIN.txt");
        HttpResponse response = new HttpResponse();

        loginController.service(request, response);

        assertAll(
            () -> Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.MOVED_PERMANENTLY),
            () -> Assertions.assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html")
        );

    }

    private HttpRequest getHttpRequest(String path) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + path));
        InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return SimpleHttpRequest.of(bufferedReader);
    }
}