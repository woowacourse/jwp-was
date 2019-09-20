package service;

import db.DataBase;
import model.AuthorizationFailException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    User user = new User("id", "password", "name", "email");

    @BeforeEach
    void setUp() {
        DataBase.addUser(user);
    }

    @Test
    void 로그인_성공() {
        UserService userService = new UserService();
        User foundUser = userService.login(user.getUserId(), user.getPassword());

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void 로그인_실패_패스워드_틀림() {
        UserService userService = new UserService();

        assertThrows(AuthorizationFailException.class, () -> {
            userService.login(user.getUserId(), "fail");
        });
    }

    @Test
    void 로그인_실패_아이디_없음() {
        UserService userService = new UserService();

        assertThrows(AuthorizationFailException.class, () -> {
            userService.login("fail", user.getPassword());
        });
    }


}