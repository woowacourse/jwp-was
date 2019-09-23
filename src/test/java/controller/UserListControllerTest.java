package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestDispatcher;
import webserver.RequestParser;
import webserver.Response;
import webserver.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @Test
    @DisplayName("로그인 상태에서 사용자 리패스트 페이지 이동 성공")
    void loggedIn() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserList.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus().getText()).isEqualTo("OK");
        assertThat(new String(res.getBody())).contains("이메일");
    }

    @Test
    @DisplayName("로그인 상태 아닐 경우 사용자 리스트 페이지 이동 실패")
    void loggedIn_failed() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserListFailed.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/user/login.html");
    }
}