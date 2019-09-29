package http.application.controller;

import db.DataBase;
import http.common.HttpHeader;
import http.common.HttpSession;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpContentType;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginControllerTest {

    private static final String INDEX_HTML = "./templates/index.html";
    private static final String LOGIN_FAILED_HTML = "./templates/user/login_failed.html";

    private LoginController loginController;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();

        httpResponse = new HttpResponse(new HttpSession(UUID.randomUUID()));
        DataBase.addUser(new User("pkch", "1234", "박경철", "chulsea@woowa.com"));
    }

    @Test
    void login_정상_흐름_테스트() throws IOException, URISyntaxException {
        InputStream in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/http_login.txt");
        httpRequest = HttpRequestParser.parse(in);

        loginController.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        HttpHeader httpHeader = httpResponse.getHttpHeader();
        assertThat(httpHeader.get("Content-Type")).isEqualTo(HttpContentType.HTML.getContentType());

        String cookieValues = httpHeader.get("Set-Cookie");
        assertTrue(cookieValues.contains("logined=true;"));
        assertTrue(cookieValues.contains("Path=/;"));

        String index = new String(FileIoUtils.loadFileFromClasspath(INDEX_HTML));
        assertTrue(index.contains("메인 페이지"));
    }

    @Test
    void login_실패_흐름_테스트() throws IOException, URISyntaxException {
        InputStream in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/http_login_failed.txt");
        httpRequest = HttpRequestParser.parse(in);

        loginController.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        HttpHeader httpHeader = httpResponse.getHttpHeader();
        assertThat(httpHeader.get("Content-Type")).isEqualTo(HttpContentType.HTML.getContentType());

        String cookieValues = httpHeader.get("Set-Cookie");
        assertTrue(cookieValues.contains("logined=false;"));
        assertTrue(cookieValues.contains("Path=/;"));

        String index = new String(FileIoUtils.loadFileFromClasspath(LOGIN_FAILED_HTML));
        assertTrue(index.contains("로그인 실패"));
    }
}
