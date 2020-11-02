package controller;

import com.google.common.net.HttpHeaders;
import common.TestFixtureFactory;
import http.HttpSession;
import http.SessionContainer;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.db.DataBase;
import model.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserListControllerTest {
    private final UserListController userListController = new UserListController();
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private HttpSession session;

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("ordincode", "1234", "윤성학", "email@email.com"));
        SessionContainer sessionContainer = SessionContainer.getInstance();
        String sessionId = sessionContainer.createSession();
        session = sessionContainer.getSession(sessionId);
    }

    @DisplayName("유저 리스트 요청 성공시 응답 테스트")
    @Test
    void userList() {
        session.setAttribute(LoginController.SESSION_KEY_OF_LOGIN, Boolean.TRUE.toString());

        Map<String, String> params = new HashMap<>();
        params.put("userId", "ordincode");
        params.put("password", "1234");

        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml");

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put(SessionContainer.SESSION_KEY_FOR_COOKIE, session.getId());

        HttpRequest httpRequest = TestFixtureFactory.makeHttpRequestForControllerTest("/user/list", header, params, cookies);

        userListController.doGet(httpRequest, new HttpResponse(outputStream));

        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 200 OK"),
                () -> assertThat(outputStream.toString()).contains("<td>ordincode</td>"),
                () -> assertThat(outputStream.toString()).contains("<td>윤성학</td>"),
                () -> assertThat(outputStream.toString()).contains("<td>email@email.com</td>")
        );
    }

    @DisplayName("유저 리스트 요청 실패시 응답 테스트 - 로그인 하지 않은 경우")
    @Test
    void userListWhenNotLogin() {
        session.setAttribute(LoginController.SESSION_KEY_OF_LOGIN, Boolean.FALSE.toString());

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put(SessionContainer.SESSION_KEY_FOR_COOKIE, session.getId());

        HttpRequest httpRequest = TestFixtureFactory.makeHttpRequestForControllerTest(new HashMap<>(), cookies);

        userListController.doGet(httpRequest, new HttpResponse(outputStream));

        assertAll(
                () -> assertThat(outputStream.toString()).contains("HTTP/1.1 302 FOUND"),
                () -> assertThat(outputStream.toString()).contains("Location: /user/login.html")
        );
    }
}
