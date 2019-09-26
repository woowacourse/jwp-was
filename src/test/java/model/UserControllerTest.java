package model;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private static final String SUCCESS_HTML = "redirect:/index.html";
    private static final String FAILED_HTML = "/user/login_failed.html";

    private static UserController userController = new UserController();

    static {
        try {
            userController.addUser(HttpRequestFactory.create(
                    Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt")));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @DisplayName("로그인 성공 후 /index.html을 반환한다")
    public void loginSuccess() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_POST_USER_LOGIN.txt"));
        assertThat(userController.login(httpRequest)).isEqualTo(SUCCESS_HTML);
    }

    @Test
    @DisplayName("비밀번호가 틀릴 때 예외를 발생시킨다")
    public void loginFailWhenPasswordNotMatch() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        assertThat(userController.login(httpRequest)).isEqualTo(FAILED_HTML);
    }

    @Test
    @DisplayName("유저가 없을 때 예외를 발생시킨다")
    public void loginFailWhenUserNotFound() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_POST_USER_LOGIN_FAIL_NOT_FOUND.txt"));
        assertThat(userController.login(httpRequest)).isEqualTo(FAILED_HTML);
    }
}