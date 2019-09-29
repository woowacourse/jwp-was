package controller;

import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import session.Session;
import session.SessionRepository;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {

    @Test
    void 로그인_하고_유저_리스트_접근() throws IOException {
        Session session = SessionRepository.create();
        String requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: JSESSIONID=" + session.getId()+ "; logined=true\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        HttpRequest httpRequest = RequestFactory.createHttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(httpRequest);
        LoginController loginController = new LoginController();
        loginController.service(httpRequest, httpResponse);

        assertThat(session.getAttribute("logined")).isEqualTo(true);

    }
}
