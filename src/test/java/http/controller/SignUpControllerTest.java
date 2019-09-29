package http.controller;

import http.model.HttpRequest;
import http.model.HttpStatus;
import http.supoort.HttpRequestParser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class SignUpControllerTest {
    @Test
    void GET_정상_요청_테스트() {
        User user = new User("andole", "password", "andole", "andole@andole.com");
        String request = "GET /user/create?userId=andole&password=password&name=andole&email=andole@andole.com HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        Controller controller = new SignUpController();
        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void POST_정상_요청_테스트() {
        String request = "POST /user/create?name=JasSung HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 46\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&email=a@b.c";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        Controller controller = new SignUpController();
        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}