package webserver.user;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ModelAndView;
import view.View;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.http.session.Cookie;
import webserver.http.session.Session;
import webserver.http.session.SessionManager;
import webserver.http.session.SessionStorage;
import webserver.staticfile.StaticFileMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {
    UserListController userListController = new UserListController();

    @DisplayName("사용자 목록 조회: 로그인한 상태라면, 사용자 목록을 동적으로 반환한다.")
    @Test
    void listTest() throws IOException {
        byte[] expected = createExpected();
        Session session = SessionManager.getNewSession();
        SessionStorage.add(session);
        Map<String, Object> header = createHeader(session.getId());
        HttpRequest httpRequest = new HttpRequest(null, header, null);
        HttpResponse httpResponse = new HttpResponse(null);

        userListController.doGet(httpRequest, httpResponse);
        assertThat(httpResponse.getBody()).isEqualTo(expected);
    }

    private byte[] createExpected() throws IOException {
        Collection<User> users = UserService.findUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String location = "/user/list.html";
        View view = new View(StaticFileMatcher.findStaticFilePath(location));
        ModelAndView modelAndView = new ModelAndView(view, model);

        return modelAndView.render().getBytes();
    }

    private Map<String, Object> createHeader(String sessionId) {
        Map<String, Object> headers = new HashMap<>();
        Cookie cookie = new Cookie();
        cookie.add("logined", sessionId);
        headers.put("Cookie", cookie);

        return headers;
    }

    @DisplayName("사용자 목록 조회: 로그인하지 않은 상태라면, 로그인 페이지로 이동한다.")
    @Test
    void listFailTest() {
        Map<String, Object> header = createHeader("notValidId");
        HttpRequest httpRequest = new HttpRequest(null, header, null);
        HttpResponse httpResponse = new HttpResponse(null);

        userListController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaders().get("Location")).isEqualTo("http://localhost:8080/user/login");
    }


}
