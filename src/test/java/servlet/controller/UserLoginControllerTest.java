package servlet.controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.resolver.UserResolver;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class UserLoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginControllerTest.class);

    private static UserService userService = new UserService();

    static {
        try {
            HttpRequest httpRequest = HttpRequestFactory.create(
                    Common.getBufferedReaderOfTextFile("HTTP_POST_USER_CREATE.txt"));
            userService.addUser(UserResolver.resolve(httpRequest));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @DisplayName("/user/login에 대한 POST 요청 성공시 /index.html로 redirect")
    public void doPost() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new UserLoginController();
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN.txt"));
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
                Common.getBufferedReaderOfTextFile("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }
}