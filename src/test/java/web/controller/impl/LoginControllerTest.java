package web.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.db.DataBase;
import web.model.User;
import webserver.StaticFile;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.message.response.ResponseCookie;
import webserver.session.HttpSession;
import webserver.session.SessionContextHolder;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest extends RequestHelper {
    private static final String INDEX_PAGE_URL = "/";
    private static final String LOGIN_FAILED_PAGE_URL = "/user/login_failed.html";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String USER_LOGIN_PAGE = "/user/login.html";

    protected final String requestPostWithWeirdQuery =
            "POST /user/create HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 59\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "\n" +
                    "userId=java&password=pass&a=b";

    private Request getRequest;
    private Request postRequest;
    private LoginController loginController;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        this.getRequest = new Request(ioUtils(requestGetLogin));
        this.postRequest = new Request(ioUtils(requestPostWithQuery));
        this.loginController = new LoginController();
    }

    @Test
    @DisplayName("로그인 페이지 get")
    void getLogin() throws IOException, URISyntaxException {
        assertThat(loginController.doGet(getRequest).toBytes())
                .isEqualTo(new Response.Builder()
                        .body(new StaticFile(TEMPLATES_PATH + USER_LOGIN_PAGE))
                        .build()
                        .toBytes());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        DataBase.addUser(new User("javajigi", "password", "포비", "pobi@pobi.com"));

        assertThat(loginController.doPost(postRequest).toBytes())
                .isEqualTo(new Response.Builder()
                        .redirectUrl(INDEX_PAGE_URL)
                        .setCookie("logined=true; Path=/")
                        .build().toBytes());
    }

    @Test
    @DisplayName("존재하지 않는 userId로 로그인할 때 리다이렉트 확인")
    void loginException1() throws IOException, URISyntaxException {
        Request request = new Request(ioUtils(requestPostWithWeirdQuery));

        assertThat(loginController.doPost(request).toBytes())
                .isEqualTo(new Response.Builder()
                        .redirectUrl(LOGIN_FAILED_PAGE_URL)
                        .addCookie(new ResponseCookie.Builder("logined", "false").path("/").build())
                        .build().toBytes());
    }

    @Test
    @DisplayName("비밀번호가 틀렸을 때 리다이렉트 확인")
    void loginException2() {
        DataBase.addUser(new User("javajigi", "1234", "포비", "pobi@pobi.com"));

        assertThat(loginController.doPost(postRequest).toBytes())
                .isEqualTo(new Response.Builder()
                        .redirectUrl(LOGIN_FAILED_PAGE_URL)
                        .addCookie(new ResponseCookie.Builder("logined", "false").path("/").build())
                        .build().toBytes());
    }
}