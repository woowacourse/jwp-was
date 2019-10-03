package http.application.controller;

import db.DataBase;
import http.common.HttpCookie;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {
    private static final int LOGINED_COOKIE = 0;

    private LoginController loginController;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();

        httpResponse = new HttpResponse();
        DataBase.addUser(new User("pkch", "1234", "박경철", "chulsea@woowa.com"));
    }

    @Test
    void login_정상_흐름_테스트() throws IOException {
        InputStream in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/http_login.txt");
        httpRequest = HttpRequestParser.parse(in);

        loginController.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        HttpCookie loginedCookie = httpResponse.getHttpCookies().get(LOGINED_COOKIE);

        assertThat(loginedCookie.getName()).isEqualTo("logined");
        assertThat(loginedCookie.getValue()).isEqualTo("true");
        assertThat(loginedCookie.getPath()).isEqualTo("/");
    }

    @Test
    void login_실패_흐름_테스트() throws IOException {
        InputStream in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/http_login_failed.txt");
        httpRequest = HttpRequestParser.parse(in);

        loginController.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        HttpCookie httpCookie = httpResponse.getHttpCookies().get(LOGINED_COOKIE);

        assertThat(httpCookie.getName()).isEqualTo("logined");
        assertThat(httpCookie.getValue()).isEqualTo("false");
        assertThat(httpCookie.getPath()).isEqualTo("/");
    }
}
