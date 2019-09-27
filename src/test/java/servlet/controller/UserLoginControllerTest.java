package servlet.controller;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import model.User;
import model.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static testhelper.Common.getBufferedReaderOfTextFile;

public class UserLoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginControllerTest.class);

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
                getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 실패했을때 /user/login_failed.html로 redirect")
    public void doPostWhenLoginFail() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new UserLoginController();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }
}