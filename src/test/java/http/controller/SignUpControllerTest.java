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
    void 정상_요청_테스트() {
        User user = new User("andole", "password", "andole", "andole@andole.com");
        String request = "GET /user/create?userId=andole&password=password&name=andole&email=andole@andole.com HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        Controller handler = new SignUpController();
        assertThat(handler.handle(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
    //TODO: 실패 테스트
}