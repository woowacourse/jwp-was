package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import model.User;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import session.Session;
import session.SessionRepository;
import view.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

import static http.Cookie.LOGINED;
import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {
    static {
        DataBase.addUser(new User("a", "a", "a", "a@a.com"));
    }

    private String requestLines;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private UserListController userListController = new UserListController();
    private ModelAndView modelAndView;

    @Test
    void 로그인_없이_유저_리스트_접근() throws IOException {
        requestLines = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = userListController.service(httpRequest, httpResponse);

        assertThat(modelAndView.getView()).isEqualTo("/user/login.html");
        assertThat(modelAndView.isRedirect()).isEqualTo(true);
    }

    @Test
    void 로그인_하고_유저_리스트_접근() throws IOException {
        Session session = SessionRepository.create();
        session.setAttribute(LOGINED, true);

        requestLines = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cookie: JSESSIONID=" + session.getId() + "\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = userListController.service(httpRequest, httpResponse);

        assertThat(modelAndView.getView()).isEqualTo("/user/list");
        assertThat(modelAndView.isRedirect()).isEqualTo(false);
    }
}
