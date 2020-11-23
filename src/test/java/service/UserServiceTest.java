package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import db.DataBase;
import exception.NotExistUserException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import model.domain.User;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    @DisplayName("회원가입")
    void join() throws IOException {
        String filePath = "src/test/resources/input/post_api_request_join.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        UserService userService = UserService.getInstance();
        userService.createUser(httpRequest);

        Collection<User> users = DataBase.findAll();
        User user = DataBase.findUserById("javajigi");

        assertAll(() -> {
            assertThat(users).hasSize(1);
            assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            assertThat(user.getUserId()).isEqualTo("javajigi");
        });
    }

    @Test
    @DisplayName("로그인")
    void login() throws IOException, NotExistUserException {
        String joinFilePath = "src/test/resources/input/post_api_request_join.txt";
        String loginFilePath = "src/test/resources/input/post_api_request_login.txt";
        InputStream joinInputStream = new FileInputStream(joinFilePath);
        InputStream loginInputStream = new FileInputStream(loginFilePath);
        HttpRequest joinHttpRequest = HttpRequest.of(joinInputStream);
        HttpRequest loginHttpRequest = HttpRequest.of(loginInputStream);

        UserService userService = UserService.getInstance();

        userService.createUser(joinHttpRequest);
        User user = userService.login(loginHttpRequest);

        assertAll(() -> {
            assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
            assertThat(user.getUserId()).isEqualTo("javajigi");
        });
    }

    @Test
    @DisplayName("로그인 - 존재하지 않는 회원")
    void login_IfNotExistUser_ThrowException() throws IOException {
        String joinFilePath = "src/test/resources/input/post_api_request_join.txt";
        String loginFilePath = "src/test/resources/input/post_api_request_login_not_exist_user.txt";
        InputStream joinInputStream = new FileInputStream(joinFilePath);
        InputStream loginInputStream = new FileInputStream(loginFilePath);
        HttpRequest joinHttpRequest = HttpRequest.of(joinInputStream);
        HttpRequest loginHttpRequest = HttpRequest.of(loginInputStream);

        UserService userService = UserService.getInstance();

        userService.createUser(joinHttpRequest);

        assertThatThrownBy(() -> userService.login(loginHttpRequest))
            .isInstanceOf(NotExistUserException.class)
            .hasMessage("Not Exist User");
    }
}
