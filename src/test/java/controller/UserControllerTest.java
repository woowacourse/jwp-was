package controller;

import db.DataBase;
import model.User;
import model.builder.UserBuilder;
import model.http.Cookie;
import model.http.HttpRequest;
import model.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.HttpStatus;
import utils.RequestDispatcher;
import utils.RequestHeaderParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {
    private static final String TEST_DATA_DIRECTORY = "src/test/java/data";

    private HttpRequest request;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        response = HttpResponse.of();
    }

    @Test
    void Login_success() throws IOException {
        request = generateHttpRequest(TEST_DATA_DIRECTORY + "/LoginHttpRequest.txt");
        User loginTargetUser = new User("javajigi", "password", "sloth", "javajigi@slipp.net");
        DataBase.addUser(loginTargetUser);
        response = RequestDispatcher.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
        assertThat(response.getCookies().get("logined")).isEqualTo(new Cookie("logined", "true"));
        assertThat(response.getPath()).isEqualTo("/index.html");
    }

    @Test
    void Login_fail() throws IOException {
        request = generateHttpRequest(TEST_DATA_DIRECTORY + "/LoginHttpRequest.txt");
        User otherUser = new User("tiger", "tiger", "tiger", "tiger@sugar.haohe");
        DataBase.addUser(otherUser);
        response = RequestDispatcher.handle(request, response);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getCookies().get("logined")).isEqualTo(new Cookie("logined", "false"));
        assertThat(response.getPath()).isEqualTo("/user/login_failed.html");
    }

    @Test
    void user_list_fail() throws IOException {
        request = generateHttpRequest(TEST_DATA_DIRECTORY + "/UserListHttpRequest.txt");
        UserBuilder builder = new UserBuilder();
        User user1 = builder
                .setUserId("user1")
                .setName("user1")
                .setPassword("user1")
                .setEmail("user1@user.com")
                .build();
        User user2 = builder
                .setUserId("user2")
                .setName("user2")
                .setPassword("user2")
                .setEmail("user2@user.com")
                .build();
        User user3 = builder
                .setUserId("user3")
                .setName("user3")
                .setPassword("user3")
                .setEmail("user3@user.com")
                .build();
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        DataBase.addUser(user3);

        response = RequestDispatcher.handle(request, response);
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
        assertThat(response.getCookies().get("logined")).isEqualTo(new Cookie("logined", "false"));
        assertThat(response.getPath()).isEqualTo("/user/login.html");
    }

    @Test
    void user_list_success() throws IOException {
        User loginTargetUser = new User("javajigi", "password", "sloth", "javajigi@slipp.net");
        DataBase.addUser(loginTargetUser);
        request = generateHttpRequest(TEST_DATA_DIRECTORY + "/LoginHttpRequest.txt");
        response = RequestDispatcher.handle(request, response);

        request = generateHttpRequest(TEST_DATA_DIRECTORY + "/UserListHttpRequest.txt");
        UserBuilder builder = new UserBuilder();
        User user1 = builder
                .setUserId("user1")
                .setName("user1")
                .setPassword("user1")
                .setEmail("user1@user.com")
                .build();
        User user2 = builder
                .setUserId("user2")
                .setName("user2")
                .setPassword("user2")
                .setEmail("user2@user.com")
                .build();
        User user3 = builder
                .setUserId("user3")
                .setName("user3")
                .setPassword("user3")
                .setEmail("user3@user.com")
                .build();
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        DataBase.addUser(user3);

        response = RequestDispatcher.handle(request, response);
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getCookies().get("logined")).isEqualTo(new Cookie("logined", "true"));
        assertThat(response.getPath()).isEqualTo("/user/list.html");
    }

    private HttpRequest generateHttpRequest(String filePath) throws IOException {
        return RequestHeaderParser.parseRequest(new InputStreamReader(new FileInputStream(new File(filePath))));
    }
}