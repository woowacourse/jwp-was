package controller.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import service.user.UserService;
import user.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Test
    void doPost() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        given(userService.findByUserId(anyString())).willReturn(new User("userId", "password", "name", "email"));

        loginController.doPost(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertAll(
            () -> assertThat(result).contains("HTTP/1.1 302 Found"),
            () -> assertThat(result).contains("Location: http://localhost:8080/index.html"),
            () -> assertThat(result).contains("Set-Cookie: SESSIONID=")
        );
    }

    @Test
    void loginFailed() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        given(userService.findByUserId(anyString())).willReturn(null);

        loginController.doPost(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertAll(
            () -> assertThat(result).contains("HTTP/1.1 302 Found"),
            () -> assertThat(result).contains("Location: http://localhost:8080/user/login_failed.html"),
            () -> assertThat(result).contains("Set-Cookie: SESSIONID=")
        );
    }

    @Test
    void parameterNotExist() throws IOException {
        String request = "POST /admin/ HTTP/1.1\n";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        loginController.doPost(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains("HTTP/1.1 400 Bad Request");
    }
}
