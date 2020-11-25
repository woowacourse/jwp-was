package controller.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

import domain.HttpRequest;
import domain.HttpResponse;
import service.user.UserService;

@ExtendWith(MockitoExtension.class)
class UserCreateControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserCreateController userCreateController;

    @Test
    void doPost() throws IOException {
        String request = "POST /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Content-Length: 59\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n\n"
            + "userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        userCreateController.doPost(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertAll(
            () -> assertThat(result).contains("HTTP/1.1 302 Found"),
            () -> assertThat(result).contains("Location: http://localhost:8080/index.html"),
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

        userCreateController.doPost(httpRequest, httpResponse);

        String result = byteArrayOutputStream.toString("UTF-8");
        assertThat(result).contains("HTTP/1.1 400 Bad Request");
    }
}
