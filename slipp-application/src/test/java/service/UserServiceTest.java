package service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import db.DataBase;
import dto.JoinRequestDto;
import dto.LoginRequestDto;
import dto.UserResponseDto;
import exception.UnAuthenticationException;
import model.User;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void login_success() {
        User user = new User("javajigi", "1234", "pobi", "pobi@slipp.com");
        DataBase.addUser(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto("javajigi", "1234");

        userService.login(loginRequestDto);
    }

    @ParameterizedTest
    @CsvSource(value = {"userId:javajigi", "password:1234"}, delimiterString = ":")
    void login_fail_nullValue(String field, String value) {
        User user = new User("javajigi", "1234", "pobi", "pobi@slipp.com");
        DataBase.addUser(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto(
            field.equals("userId") ? value : null,
            field.equals("password") ? value : null);

        assertThatThrownBy(() -> userService.login(loginRequestDto))
            .isInstanceOf(UnAuthenticationException.class);
    }

    @Test
    void login_fail_wrongPassword() {
        User user = new User("javajigi", "1234", "pobi", "pobi@slipp.com");
        DataBase.addUser(user);

        LoginRequestDto loginRequestDto = new LoginRequestDto("javajigi", "1111");

        assertThatThrownBy(() -> userService.login(loginRequestDto))
            .isInstanceOf(UnAuthenticationException.class);
    }

    @Test
    void login_fail_nonExistingUser() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("javajigi", "1111");

        assertThatThrownBy(() -> userService.login(loginRequestDto))
            .isInstanceOf(UnAuthenticationException.class);
    }

    @Test
    void join() {
        JoinRequestDto joinRequestDto = new JoinRequestDto("javajigi", "1234", "pobi", "pobi@slipp.com");
        userService.join(joinRequestDto);
    }

    @Test
    void findAll() {
        userService.join(new JoinRequestDto("javajigi", "1234", "pobi", "pobi@slipp.com"));

        List<UserResponseDto> saved = userService.findAll();

        Assertions.assertThat(saved).isNotEmpty();
        assertThat(saved.size()).isEqualTo(1);
    }
}