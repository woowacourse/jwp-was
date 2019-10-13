package web.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.db.DataBase;
import web.model.User;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.session.HttpSession;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest extends RequestHelper {
    private UserListController userListController;

    @BeforeEach
    void setUp() {
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
        Response response = new Response(HttpVersion.HTTP_1_1);

        this.userListController.service(request, response);

        String actual = new String(response.toBytes());

        // then
        assertThat(actual).contains("javajigi");
        assertThat(actual).contains("포비");
        assertThat(actual).contains("pobi@pobi.com");
    }

    @Test
    @DisplayName("로그인이 안 된 상태에서 유저 리스트 페이지에 접근시 리다이렉트 확인")
    void loginFail() throws IOException, URISyntaxException {
        // given
        Request request = new Request(ioUtils(requestGetUserListWithoutLogin));
        Response response = new Response();

        // when
        this.userListController.service(request, response);
        String actual = new String(response.toBytes());

        // then
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /user/login");
    }
}