package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.view.View;
import model.User;
import org.junit.jupiter.api.Test;
import session.HttpSession;
import session.InMemoryHttpSessionContainer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private static final String ID_1 = "qweqweqwazxc";
    private static final String ID_2 = "icqeqsdasdqw";

    @Test
    void 비로그인_list_접근() throws IOException, URISyntaxException {
        String requestSting = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n";

        HttpResponse response = createResponse(requestSting);
        assertThat(response.getHeader().contains("/user/login.html")).isTrue();
    }

    @Test
    void 로그인_list_접근() throws IOException {
        User user1 = new User(ID_1, "password", "name", "email");
        User user2 = new User(ID_2, "password", "name", "email");

        DataBase.addUser(user1);
        DataBase.addUser(user2);

        HttpSession httpSession = InMemoryHttpSessionContainer.getInstance().createSession();
        httpSession.setAttribute("login-user", user1);

        String requestSting = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Cookie: SessionId=" + httpSession.getId();

        HttpResponse response = createResponse(requestSting);
        assertThat(response.getBody().contains(ID_1)).isTrue();
        assertThat(response.getBody().contains(ID_2)).isTrue();
    }

    private HttpResponse createResponse(String requestSting) throws IOException {
        UserListController controller = new UserListController();
        InputStream in = new ByteArrayInputStream(requestSting.getBytes());
        HttpRequest request = new HttpRequest(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);
        View view = controller.service(request, response);
        ResponseResolver.resolve(view, response);

        return response;
    }

}