package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private static final String ID_1 = "id1";
    private static final String ID_2 = "id2";
    private HttpRequest request;

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


        String requestSting = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Cookie: logined=true";

        HttpResponse response = createResponse(requestSting);
        assertThat(new String(response.getBody()).contains(ID_1)).isTrue();
        assertThat(new String(response.getBody()).contains(ID_2)).isTrue();
    }

    private HttpResponse createResponse(String requestSting) throws IOException {
        UserListController controller = new UserListController();
        InputStream in = new ByteArrayInputStream(requestSting.getBytes());
        HttpRequest request = new HttpRequest(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);
        controller.doGet(request, response);

        return response;
    }

}