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

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    static {
        DataBase.addUser(new User("a","a","a", "a@a.com"));
    }

    @Test
    void 로그인_성공() throws IOException {
        Session session =SessionRepository.create();
        String requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Content-Length: 19\n" +
                "Cookie: JSESSIONID=" + session.getId()+ "\n" +
                "\r\n" +
                "userId=a&password=a";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(httpRequest);
        LoginController loginController = new LoginController();
        loginController.service(httpRequest, httpResponse);

        assertThat(httpResponse.)
        assertThat(session.getAttribute("logined")).isEqualTo(true);
    }

    @Test
    void 로그인_실패() throws IOException {
        String requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: JSESSIONID=jsessionId\n" +
                "\n" +
                "userId=exception&password=exception";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(httpRequest);
        LoginController loginController = new LoginController();
        Session session = SessionRepository.getSession("jesssionId");
        loginController.service(httpRequest, httpResponse);
        assertThat(session.getAttribute("logined")).isEqualTo(false);
    }
}