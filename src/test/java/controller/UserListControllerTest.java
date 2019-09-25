package controller;

import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import http.HttpSession;
import jdk.internal.joptsimple.internal.Strings;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.View;
import webserver.SessionManager;

import java.io.IOException;

import static com.google.common.net.HttpHeaders.COOKIE;
import static http.HttpRequestTest.POST_REQUEST;
import static http.HttpRequestTest.USER_LIST_REQUEST;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.IOUtils.convertStringToInputStream;

public class UserListControllerTest {
    private UserListController userListController = new UserListController();

    private String sessionId;

    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    @BeforeEach
    void setUp() {
        HttpSession session = SessionManager.createEmptySession();
        session.setAttributes("user", DataBase.findUserById(ID).get());
        sessionId = session.getId();
        SessionManager.addSession(session);
    }

    @Test
    void 유저_로그인시_유저_목록_접근() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(USER_LIST_REQUEST, COOKIE + ": SESSIONID=" + sessionId)));
        HttpResponse response = new HttpResponse();

        View view = userListController.service(request, response);

        assertThat(view.isRedirectView()).isFalse();
        assertThat(view.getViewName()).isEqualTo("user/list.html");
    }

    @Test
    void 유저_비로그인시_유저_목록_접근() throws IOException {
        HttpRequest request = HttpRequestParser.parse(
                convertStringToInputStream(String.format(USER_LIST_REQUEST, Strings.EMPTY)));
        HttpResponse response = new HttpResponse();

        View view = userListController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("user/login.html");
    }

    @Test
    void POST_메소드_에러() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(POST_REQUEST));
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> userListController.service(request, response));
    }
}