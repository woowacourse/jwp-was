package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.QueryParameter;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.HttpSessionManager;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserListControllerTest {

    @Test
    @DisplayName("로그인 상태에서 유저리스트 출력")
    void doGet() {
        User user = new User("testUser", "test", "test", "test");
        DataBase.addUser(user);
        HttpSession httpSession = HttpSessionManager.getInstance().createSession();

        httpSession.setAttribute("logined", "true");

        HttpRequestLine httpRequestLine = new HttpRequestLine("GET /user/list HTTP/1.1");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Cookie", "JSESSIONID=" + httpSession.getId());
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(headers);
        HttpRequestBody httpRequestBody = new HttpRequestBody(new byte[0]);
        HttpRequest httpRequest = new HttpRequest(httpRequestLine, httpRequestHeader, QueryParameter.empty(), httpRequestBody);

        HttpResponse httpResponse = new HttpResponse();

        UserListController userListController = new UserListController();

        userListController.doGet(httpRequest, httpResponse);

        byte[] body = httpResponse.getHttpResponseBody().getBody();
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 200 OK\r\n");
        assertTrue(new String(body).contains("testUser"));

        DataBase.removeUser(user);
    }
}