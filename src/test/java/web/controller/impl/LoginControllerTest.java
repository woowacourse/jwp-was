package web.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.db.DataBase;
import web.model.User;
import webserver.message.HttpCookie;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.HttpSession;
import webserver.session.SessionContextHolder;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest extends RequestHelper {
    private final String requestPostWithWeirdQuery =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 59\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=java&password=pass&a=b";

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        this.loginController = new LoginController();
    }

    @Test
    @DisplayName("로그인 페이지 get")
    void getLogin() throws IOException, URISyntaxException {
        // given
        Request request = new Request(ioUtils(requestGetLogin));
        Response response = new Response();

        // when
        this.loginController.service(request, response);

        String actual = new String(response.toBytes());

        // then
        assertThat(actual).contains("200 OK");
        assertThat(actual).contains("<button type=\"submit\" class=\"btn btn-success clearfix pull-right\">로그인</button>");
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws IOException, URISyntaxException {
        // given
        DataBase.addUser(new User("javajigi", "password", "포비", "pobi@pobi.com"));

        HttpSession session = HttpSession.newInstance();
        SessionContextHolder.addSession(session);

        Request request = new Request(ioUtils(requestPostWithQuery));
        Response response = new Response();

        // when
        this.loginController.service(request, response);
        response.addCookie(new HttpCookie.Builder("JSESSIONID", "12345").path("/").build());

        String actual = new String(response.toBytes());

        // then
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /");
        assertThat(actual).contains("Set-Cookie: JSESSIONID=12345; Path=/; ");
    }

    @Test
    @DisplayName("존재하지 않는 userId로 로그인할 때 리다이렉트 확인")
    void loginException1() throws IOException, URISyntaxException {
        // given
        Request request = new Request(ioUtils(requestPostWithWeirdQuery));
        Response response = new Response();

        // when
        this.loginController.service(request, response);

        // then
        String actual = new String(response.toBytes());
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /user/login_failed.html");
        assertThat((boolean) request.getSession().getAttribute("logined")).isEqualTo(false);
    }

    @Test
    @DisplayName("비밀번호가 틀렸을 때 리다이렉트 확인")
    void loginException2() throws IOException, URISyntaxException {
        // given
        DataBase.addUser(new User("javajigi", "1234", "포비", "pobi@pobi.com"));

        Request request = new Request(ioUtils(requestPostWithQuery));
        Response response = new Response();

        // when
        this.loginController.service(request, response);

        // then
        String actual = new String(response.toBytes());
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /user/login_failed.html");
        assertThat((boolean) request.getSession().getAttribute("logined")).isEqualTo(false);
    }
}