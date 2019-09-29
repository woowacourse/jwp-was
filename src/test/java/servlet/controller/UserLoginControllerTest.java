package servlet.controller;

import domain.User;
import domain.UserService;
import http.request.HttpRequest;
import http.request.support.HttpRequestFactory;
import http.response.HttpResponse;
import http.session.support.RandomKeyGenerator;
import http.session.support.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static testhelper.Common.getBufferedReaderOfTextFile;

public class UserLoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginControllerTest.class);

    private static SessionManager sessionManager = new SessionManager(new RandomKeyGenerator());

    private UserService userService = new UserService();

    @BeforeEach
    public void setUp() {
        userService.addUser(
                new User("javajigi", "password", "name", "javajigi@mail.com"));
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 성공시 /index.html로 redirect")
    public void doPostWhenLoginSuccess() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new UserLoginController();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN.txt"), sessionManager);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpRequest.addSessionAttribute("logined", "true");
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
        BufferedReader bufferedReader = Common.convertToBufferedReader(byteArrayOutputStream);
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 302 FOUND");
        assertThat(bufferedReader.readLine()).isEqualTo("Content-Type: text/html");
        assertThat(bufferedReader.readLine()).isEqualTo("Location: /index.html");
        assertThat(bufferedReader.readLine()).isEqualTo("Set-Cookie: Path=/;");
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 실패했을때 /user/login_failed.html로 이동")
    public void doPostWhenLoginFail() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new UserLoginController();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"), sessionManager);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
        BufferedReader bufferedReader = Common.convertToBufferedReader(byteArrayOutputStream);
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(bufferedReader.readLine()).isEqualTo("Content-Length: 4991");
        assertThat(bufferedReader.readLine()).isEqualTo("Content-Type: text/html");
        assertThat(bufferedReader.readLine()).isEqualTo("Set-Cookie: Path=/;");
    }
}