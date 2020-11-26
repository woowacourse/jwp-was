package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserCreateControllerTest {
    @Test
    @DisplayName("UserCreateController 회원가입 후 리다이렉트 테스트")
    void doPost() throws IOException {
        String message = "POST /user/create HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 93\r\n"
            + "Content-Type: application/x-www-form-urlencoded\r\n"
            + "Accept: */*\r\n"
            + "\r\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        HttpRequest httpRequest = HttpRequest.from(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new UserCreateController().doPost(httpRequest, httpResponse);
        assertThat(out.toString()).isEqualTo("HTTP/1.1 302 Found\nLocation: /index.html\n");
    }
}
