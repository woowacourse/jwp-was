package web.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.db.DataBase;
import web.model.User;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.HttpSession;
import webserver.session.SessionContextHolder;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest extends RequestHelper {
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
        // given
        User user = new User("javajigi", "1234", "포비", "pobi@pobi.com");
        DataBase.addUser(user);

        HttpSession session = HttpSession.newInstance();
        session.setAttribute("user", user);
        SessionContextHolder.addSession(session);

        // when
        Request request = new Request(ioUtils(requestGetHeaderWithCookie + "; sessionId=" + session.getId()));
        String result = new String(this.userListController.service(request).toBytes());

        // then
        assertThat(result).contains("javajigi");
        assertThat(result).contains("포비");
        assertThat(result).contains("pobi@pobi.com");
    }

    @Test
    @DisplayName("로그인이 안 된 상태에서 유저 리스트 페이지에 접근시 리다이렉트 확인")
    void loginFail() {
        assertThat(this.userListController.service(this.request).toBytes())
                .isEqualTo(new Response.Builder().redirectUrl("/user/login").build().toBytes());
    }
}