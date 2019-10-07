package http.application.controller;

import db.DataBase;
import http.application.Controller;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserListControllerTest {

    private Controller controller;
    private InputStream in;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    private User user;

    @BeforeEach
    void setUp() {
        controller = new UserListController();
        httpResponse = new HttpResponse();

        user = new User("pkch", "1234", "철시", "pkch@woowa.com");
        DataBase.addUser(user);

    }

    @Test
    void user_list_접근_정상_흐름() throws IOException {
        in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/user_list.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        String httpResponseBody = new String(httpResponse.getHttpResponseBody().getBody());
        assertTrue(httpResponseBody.contains(user.getUserId()));
        assertTrue(httpResponseBody.contains(user.getName()));
        assertTrue(httpResponseBody.contains(user.getEmail()));
    }

    @Test
    void 로그인_안된_유저_접근시_login_페이지_리다이렉팅() throws IOException {
        in = new FileInputStream(BasicControllerTest.TEST_RESOURCES + "/not_login_user_list.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpResponse.getHeader("Location")).isEqualTo("/user/login.html");
    }
}
