package http.controller;

import http.model.HttpRequest;
import http.supoort.HttpRequestParser;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestHandlerTest {
    @Test
    void name() {
        User user = new User("andole", "password", "andole", "andole@andole.com");
        String request = "GET /user/create?userId=andole&password=password&name=andole&email=andole@andole.com HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        HttpRequestHandler handler = new UserRequestHandler();
        User result = (User) handler.handle(httpRequest).getModel().getAttributes("user");

        assertThat(result).isEqualTo(user);
    }
}