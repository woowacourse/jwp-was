package servlet;

import db.DataBase;
import http.HttpMimeType;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static http.HttpHeaders.LOCATION;
import static http.HttpHeaders.SET_COOKIE;
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
        String requestMessage = makeLoginRequestMessage("id", "password");
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponse response = new HttpResponse();

        loginServlet.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getHeaders().getHeader(LOCATION))
                .isEqualTo("/index.html");
        assertThat(response.getHeaders().getHeader(SET_COOKIE))
                .isEqualTo("logined=true; Path=/");
    }

    @Test
    @DisplayName("비밀번호 불일치시 /user/login_failed.html 이동")
    void loginFailPasswordMismatch() throws URISyntaxException, IOException {
        String requestMessage = makeLoginRequestMessage("id", "passwor");
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponse response = new HttpResponse();

        loginServlet.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getHeaders().getHeader(LOCATION))
                .isEqualTo("/user/login_failed.html");
        assertThat(response.getHeaders().getHeader(SET_COOKIE))
                .isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("유저가 없을 때 /user/login_failed.html 이동")
    void loginFailNotFoundUser() throws URISyntaxException, IOException {
        String requestMessage = makeLoginRequestMessage("notFoundUser", "password");
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
        HttpResponse response = new HttpResponse();

        loginServlet.handle(request, response);

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getHeaders().getHeader(LOCATION))
                .isEqualTo("/user/login_failed.html");
        assertThat(response.getHeaders().getHeader(SET_COOKIE))
                .isEqualTo("logined=false; Path=/");
    }

    private static String makeLoginRequestMessage(String userId, String password) {
        String body = "userId=" + userId + "&password=" + password;
        return "POST /user/login HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Content-Type: " + HttpMimeType.X_WWW_FORM_URLENCODED + "\n"
                + "Content-Length: " + body.length() + "\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "\n"
                + body;
    }
}
