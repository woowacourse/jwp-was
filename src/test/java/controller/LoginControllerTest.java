package controller;

import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.View;
import webserver.SessionManager;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static http.HttpRequestTest.GET_REQUEST;
import static http.HttpRequestTest.LOGIN_REQUEST;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.IOUtils.convertStringToInputStream;

public class LoginControllerTest {
    private static final String LOGIN_SUCCESS_COOKIE_PATTERN = "logined=true; Path=/";
    private static final String LOGIN_FAILURE_COOKIE_PATTERN = "logined=false; Path=/";

    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    private LoginController loginController = new LoginController();
    private String sessionId;

    @BeforeEach
    void setUp() {
        HttpSession session = SessionManager.createEmptySession();
        session.setAttributes("user", DataBase.findUserById(ID).get());
        sessionId = session.getId();
        SessionManager.addSession(session);
    }

    @Test
    void 유저_로그인_성공() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(createRawHttpRequest(ID, PASSWORD)));
        HttpResponse response = new HttpResponse();

        View view = loginController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("index.html");
        assertThat(response.getHeader(SET_COOKIE)).containsPattern(LOGIN_SUCCESS_COOKIE_PATTERN);
    }

    @Test
    void 없는_유저_아이디로_로그인() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(createRawHttpRequest("ABC", PASSWORD)));
        HttpResponse response = new HttpResponse();

        View view = loginController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("user/login_failed.html");
        assertThat(response.getHeader(SET_COOKIE)).containsPattern(LOGIN_FAILURE_COOKIE_PATTERN);
    }

    @Test
    void 다른_비밀번호로_로그인() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(createRawHttpRequest(ID, "PASS")));
        HttpResponse response = new HttpResponse();

        View view = loginController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("user/login_failed.html");
        assertThat(response.getHeader(SET_COOKIE)).containsPattern(LOGIN_FAILURE_COOKIE_PATTERN);
    }

    @Test
    void GET_메소드_에러() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(GET_REQUEST));
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> loginController.service(request, response));
    }

    private String createRawHttpRequest(String id, String password) {
        return String.format(LOGIN_REQUEST, "SESSIONID=" + sessionId, id, password);
    }
}
