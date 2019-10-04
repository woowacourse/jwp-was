package controller;

import db.DataBase;
import http.common.Cookie;
import http.common.HttpSession;
import http.common.Parameters;
import http.common.SessionManager;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void doGetTest() throws Exception {
        User user = new User("cony", "password", "cony", "cony@cony.com");
        DataBase.addUser(user);

        HttpSession httpSession = SessionManager.createSession();
        httpRequest = HttpRequest.of(
                RequestLine.of("GET /user/list.html HTTP/1.1"),
                RequestHeader.of(new ArrayList<>()),
                new Parameters(),
                new ArrayList<>(Arrays.asList(new Cookie("JSESSIONID", httpSession.getId())))
                );
        httpResponse = HttpResponse.of();
        httpResponse.addHeaderFromRequest(httpRequest);

        httpSession.setAttribute("logined", true);
        httpResponse.setSession(httpSession);

        UserListController userListController = new UserListController();
        userListController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("200 OK");
        assertThat(httpResponse.toString()).contains("JSESSIONID");
        assertThat(new String(httpResponse.getBody(), StandardCharsets.UTF_8)).contains("cony@cony.com");
    }
}