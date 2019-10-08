package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestResourceLoader;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpStatus.FOUND;
import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private UserController userController = new UserController();

    @Test
    void user_생성() throws IOException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_POST.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());

        userController.doPost(request, response);

        assertThat(response.getMessageHeader())
                .isEqualTo(request.getVersion() + " " + FOUND.getMessage() + "\r\n"
                        + "Location: /index.html\r\n");
    }

    @Test
    @DisplayName("로그인한 유저가 /user/list로 접근하면 사용자 목록을 보여준다.")
    void returnUserList_ifRequestingUser_isLogined() throws IOException, URISyntaxException {
        // Given
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Userlist_When_Login.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());

        addUsers();

        // When
        userController.doGet(request, response);

        // Then
        String responseBody = new String(response.getBody());
        assertThat(responseBody).contains(
                "user1", "user2", "user3",
                "userName1", "userName2", "userName3",
                "user1@user.com", "user2@user.com", "user3@user.com"
        );
    }

    @Test
    @DisplayName("로그인하지 않은 유저가 /user/list로 접근하면 login 페이지를 보여준다.")
    void returnUserList_ifRequestingUser_isNotLogined() throws IOException, URISyntaxException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Userlist_When_Logout.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());

        userController.doGet(request, response);

        assertThat(response.getHeader("Location")).isEqualTo("/user/login.html");
    }

    private void addUsers() {
        User user1 = new User("user1", "password", "userName1", "user1@user.com");
        User user2 = new User("user2", "password", "userName2", "user2@user.com");
        User user3 = new User("user3", "password", "userName3", "user3@user.com");

        DataBase.addUser(user1);
        DataBase.addUser(user2);
        DataBase.addUser(user3);
    }

}