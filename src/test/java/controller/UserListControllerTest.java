package controller;

import common.TestFileIo;
import db.DataBase;
import http.request.Request;
import http.response.Response;
import http.session.HttpSessionStore;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.MockSession;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserListControllerTest {
    @DisplayName("로그인이 되어있을때 리스트를 확인")
    @Test
    void doGetTest() throws Exception {
        User pobi = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User jun = new User("jun", "password", "메이커준", "jun@gmail.com");

        DataBase.addUser(pobi);
        DataBase.addUser(jun);
        MockSession mockSession = new MockSession(null, "1");
        HttpSessionStore.addSession(mockSession);

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_user_list_request.txt"));
        Response response = new Response(result);

        UserListController userListController = new UserListController();
        userListController.doGet(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 200 OK "),
                () -> assertThat(actual).contains("Content-Type: text/html;charset=UTF-8 "),
                () -> assertThat(actual).contains("javajigi"),
                () -> assertThat(actual).contains("자바지기"),
                () -> assertThat(actual).contains("javajigi@gmail.com"),
                () -> assertThat(actual).contains("jun"),
                () -> assertThat(actual).contains("메이커준"),
                () -> assertThat(actual).contains("jun@gmail.com")
        );
    }

    @DisplayName("로그인이 되어있지 않다면 login.html으로 리다이렉트")
    @Test
    void doGetTest2() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_user_list_not_login_request.txt"));
        Response response = new Response(result);

        UserListController userListController = new UserListController();
        userListController.doGet(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
            () -> assertThat(actual).contains("Location: /user/login.html ")
        );
    }
}
