package view;

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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsViewTest {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void renderTest() throws IOException {
        DataBase.addUser(new User("cony", "password", "cony", "cony@cony.com"));
        Map<String, Object> model = new HashMap<>();
        model.put("users", DataBase.findAll());

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

        HandlebarsView view = new HandlebarsView("user/list.html");
        view.render(model, httpRequest, httpResponse);

        assertThat(new String(httpResponse.getBody(), StandardCharsets.UTF_8)).contains("cony@cony.com");
    }
}