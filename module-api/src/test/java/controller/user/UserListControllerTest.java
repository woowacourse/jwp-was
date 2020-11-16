package controller.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.session.SessionService;
import service.user.UserService;
import session.HttpSession;
import user.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

@ExtendWith(MockitoExtension.class)
class UserListControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    private UserListController userListController;

    @BeforeEach
    void setUp() {
        userListController = new UserListController(userService, sessionService, new ObjectMapper());
    }

    @Test
    void doGet() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cookie: SESSIONID=aaa";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", true);
        httpRequest.setHttpSession(httpSession);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        given(userService.list()).willReturn(
            Collections.singletonList(new User("userId", "password", "name", "email")));

        userListController.doGet(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains(
            "\"userId\":\"userId\",\"password\":\"password\",\"name\":\"name\",\"email\":\"email\"");
    }

    @Test
    void sessionNotExist() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cookie: SESSIONID=aaa";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute("logined", false);
        httpRequest.setHttpSession(httpSession);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        userListController.doGet(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains("HTTP/1.1 401 Unauthorized");
    }
}
