package servlet;

import db.DataBase;
import http.HttpMimeType;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponseEntity;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static http.HttpHeaders.LOCATION;
import static http.response.HttpStatus.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginServletTest {
    private Servlet loginServlet;

    @BeforeEach
    void setUp() {
        loginServlet = new LoginServlet();
        User user = new User("id", "password", "name", "woowa@woo.wa");
        DataBase.addUser(user);
    }

    @Test
    @DisplayName("로그인 성공시 index.html로 이동")
    void loginSuccess() throws URISyntaxException, IOException {
        String body = "userId=id&password=password";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Content-Type: " + HttpMimeType.X_WWW_FORM_URLENCODED + "\n"
                + "Content-Length: " + body.length() + "\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "\n"
                + body;
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponseEntity responseEntity = loginServlet.handle(request);

        assertThat(responseEntity.getStatus()).isEqualTo(FOUND);
        assertThat(responseEntity.getHeaders().getHeader(LOCATION))
                .isEqualTo("/index.html");
    }

    @Test
    @DisplayName("비밀번호 불일치시 /user/login_failed.html 이동")
    void loginFailPasswordMismatch() throws URISyntaxException, IOException {
        String body = "userId=id&password=passwor";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Content-Type: " + HttpMimeType.X_WWW_FORM_URLENCODED + "\n"
                + "Content-Length: " + body.length() + "\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "\n"
                + body;
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponseEntity responseEntity = loginServlet.handle(request);

        assertThat(responseEntity.getStatus()).isEqualTo(FOUND);
        assertThat(responseEntity.getHeaders().getHeader(LOCATION))
                .isEqualTo("/user/login_failed.html");
    }

    @Test
    @DisplayName("유저가 없을 때 /user/login_failed.html 이동")
    void loginFailNotFoundUser() throws URISyntaxException, IOException {
        String body = "userId=notFoundUser&password=password";
        String requestMessage = "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Content-Type: " + HttpMimeType.X_WWW_FORM_URLENCODED + "\n"
                + "Content-Length: " + body.length() + "\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "\n"
                + body;
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponseEntity responseEntity = loginServlet.handle(request);

        assertThat(responseEntity.getStatus()).isEqualTo(FOUND);
        assertThat(responseEntity.getHeaders().getHeader(LOCATION))
                .isEqualTo("/user/login_failed.html");
    }
}
