package domain.user.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.user.model.User;
import domain.user.service.UserService;
import webserver.HttpRequest;
import webserver.HttpResponse;

@ExtendWith(MockitoExtension.class)
class UserReadControllerTest {
    private UserReadController userReadController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        userReadController = new UserReadController(userService, new ObjectMapper());
    }

    @Test
    void doGet() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        given(userService.findByUserId(null)).willReturn(new User("userId", "password", "name", "email"));

        userReadController.doGet(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains(
            "\"userId\":\"userId\",\"password\":\"password\",\"name\":\"name\",\"email\":\"email\"");
    }

    @Test
    void userNotExist() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password&name=JaeSung";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        given(userService.findByUserId(anyString())).willReturn(null);

        userReadController.doGet(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains("HTTP/1.1 422 Unprocessable Entity");
    }
}
