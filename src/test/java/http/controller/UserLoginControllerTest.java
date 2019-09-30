package http.controller;

import db.DataBase;
import http.common.HttpSession;
import http.common.HttpStatus;
import http.exception.NotFoundUserException;
import http.exception.NotMatchPasswordException;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.SessionHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static model.user.User.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserLoginControllerTest {
    private String testDirectory = "./src/test/resources/";

    @BeforeEach
    void setUp() {
        User user = new User("van", "1234", "van", "van@van.van");
        DataBase.addUser(user);
    }

    @Test
    void POST일_때_정상적으로_로그인되는지_확인한다() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "login_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        UserLoginController controller = new UserLoginController();
        HttpResponse response = new HttpResponse();
        controller.doPost(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        String sessionId = response.getCookie("sessionId").getValue();
        HttpSession session = SessionHandler.getInstance().getSession(sessionId);
        String userId = (String) session.getAttribute(USER_ID);
        assertThat(userId).isEqualTo("van");
    }

    @Test
    void 비밀번호_불일치_로그인_실패_테스트() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "login_fail_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        UserLoginController controller = new UserLoginController();
        HttpResponse response = new HttpResponse();
        assertThrows(NotMatchPasswordException.class, () -> controller.doPost(request, response));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
    }

    @Test
    void 존재하지_않는_유저_로그인_실패_테스트() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "login_NotExist_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        UserLoginController controller = new UserLoginController();
        HttpResponse response = new HttpResponse();
        assertThrows(NotFoundUserException.class, () -> controller.doPost(request, response));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
    }
}