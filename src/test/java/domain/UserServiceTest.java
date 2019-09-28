package domain;

import db.DataBase;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servlet.resolver.UserResolver;
import testhelper.Common;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    @DisplayName("로그인 성공 후 /index.html을 반환한다")
    public void loginSuccess() throws IOException {
        DataBase.addUser(new User("javajigi", "password", "name", "java@slipp.net"));
        HttpRequest httpRequest = Common.getHttpRequest("HTTP_POST_USER_LOGIN.txt");
        assertThat(userService.login(UserResolver.resolve(httpRequest))).isEqualTo(true);
    }

    @Test
    @DisplayName("비밀번호가 틀릴 때 false를 반환한다")
    public void loginFailWhenPasswordNotMatch() throws IOException {
        HttpRequest httpRequest = Common.getHttpRequest("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt");
        assertThat(userService.login(UserResolver.resolve(httpRequest))).isEqualTo(false);
    }

    @Test
    @DisplayName("유저가 없을 때 false를 발생시킨다")
    public void loginFailWhenUserNotFound() throws IOException {
        HttpRequest httpRequest = Common.getHttpRequest("HTTP_POST_USER_LOGIN_FAIL_NOT_FOUND.txt");
        assertThat(userService.login(UserResolver.resolve(httpRequest))).isEqualTo(false);
    }
}