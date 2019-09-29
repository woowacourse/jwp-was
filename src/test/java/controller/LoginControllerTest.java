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

class LoginControllerTest {
    static {
        DataBase.addUser(new User("a", "a", "a", "a@a.com"));
    }

    private String requestLines;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private LoginController loginController = new LoginController();
    private ModelAndView modelAndView;

    @Test
    void 로그인_성공() throws IOException {
        Session session = SessionRepository.create();
        requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Content-Length: 19\n" +
                "Cookie: JSESSIONID=" + session.getId() + "\n" +
                "\r\n" +
                "userId=a&password=a";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = loginController.service(httpRequest, httpResponse);

        assertThat(session.getAttribute(LOGINED)).isEqualTo(true);
        assertThat(modelAndView.getView()).isEqualTo("/index.html");
        assertThat(modelAndView.isRedirect()).isEqualTo(true);
    }

    @Test
    void 로그인_실패() throws IOException {
        Session session = SessionRepository.create();
        requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Content-Length: 35\n" +
                "Cookie: JSESSIONID=" + session.getId() + "\n" +
                "\n" +
                "userId=exception&password=exception";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = loginController.service(httpRequest, httpResponse);

        assertThat(session.getAttribute(LOGINED)).isEqualTo(false);
        assertThat(modelAndView.getView()).isEqualTo("/user/login_failed.html");
        assertThat(modelAndView.isRedirect()).isEqualTo(true);
    }
}