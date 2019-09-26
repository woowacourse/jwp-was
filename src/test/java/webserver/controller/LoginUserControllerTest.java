package webserver.controller;

import http.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class LoginUserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginUserControllerTest.class);

    private static UserController userController = new UserController();

    static {
        try {
            HttpRequest httpRequest = HttpRequestFactory.create(
                    Common.getBufferedReader("HTTP_POST_USER_CREATE.txt"));
            userController.addUser(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 성공시 /index.html로 redirect")
    public void doPost() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new LoginUserController();
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReader("HTTP_POST_USER_LOGIN.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 실패했을때 /user/login_failed.html로 redirect")
    public void doPostWhenLoginFail() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new LoginUserController();
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReader("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }
}