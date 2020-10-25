package webserver.user;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ModelAndView;
import view.View;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.staticfile.StaticFileMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {
    UserListController userListController = new UserListController();

    @DisplayName("사용자 목록을 동적으로 반환한다.")
    @Test
    void listTest() throws IOException {
        Collection<User> users = UserService.findUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String location = "/user/list.html";
        View view = new View(StaticFileMatcher.findStaticFilePath(location));
        ModelAndView modelAndView = new ModelAndView(view, model);

        byte[] expected = modelAndView.render().getBytes();

        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=true");
        HttpRequest httpRequest = new HttpRequest(null, headers, null);
        HttpResponse httpResponse = new HttpResponse(null);

        userListController.doGet(httpRequest, httpResponse);
        assertThat(httpResponse.getBody()).isEqualTo(expected);
    }

    @DisplayName("사용자 목록 출력 시, 로그인하지 않았다면 로그인 페이지로 이동한다.")
    @Test
    void listFailTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=false");
        HttpRequest httpRequest = new HttpRequest(null, headers, null);
        HttpResponse httpResponse = new HttpResponse(null);

        userListController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeaders().get("Location")).isEqualTo("http://localhost:8080/user/login");
    }
}
