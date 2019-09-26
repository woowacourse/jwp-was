package web.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.StaticFile;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest extends RequestHelper {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String USER_LIST_PAGE = "/user/list.html";

    private Request request;
    private UserListController userListController;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        this.request = new Request(ioUtils(requestGetUserListWithoutLogin));
        this.userListController = new UserListController();
    }

    @Test
    @DisplayName("로그인이 되어있는 상태에서 유저 리스트 페이지에 접근하는 경우")
    void loginSuccess() throws IOException, URISyntaxException {
        Request request = new Request(ioUtils(requestGetHeaderWithCookie));

        assertThat(this.userListController.service(request).toBytes())
                .isEqualTo(new Response.Builder().body(new StaticFile(TEMPLATES_PATH + USER_LIST_PAGE)).build().toBytes());
    }

    @Test
    @DisplayName("로그인이 안 된 상태에서 유저 리스트 페이지에 접근시 리다이렉트 확인")
    void loginFail() {
        assertThat(this.userListController.service(this.request).toBytes())
                .isEqualTo(new Response.Builder().redirectUrl("/user/login").build().toBytes());
    }
}