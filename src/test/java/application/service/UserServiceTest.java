package application.service;

import static org.assertj.core.api.Assertions.assertThat;

import application.db.DataBase;
import application.dto.LoginRequest;
import application.dto.UserCreateRequest;
import application.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserServiceTest {

    private final UserService userService = new UserService();
    private final UserCreateRequest userCreateRequest = new UserCreateRequest(
        "testId", "testPassword", "testName", "test@test.com");

    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void create() {
        UserResponse userResponse = userService.create(userCreateRequest);

        assertThat(userResponse).isEqualToComparingFieldByField(
            new UserResponse("testId", "testName", "test@test.com"));
    }

    @ParameterizedTest
    @CsvSource({"testId,testPassword,true",
        "wrongId,testPassword,false", "testId,wrongPassword,false"})
    void login(String id, String password, boolean expected) {
        // given
        userService.create(userCreateRequest);

        // when & then
        LoginRequest loginRequest = new LoginRequest(id, password);
        assertThat(userService.isExistUser(loginRequest)).isEqualTo(expected);
    }

    @Test
    void findUsers() {
        List<UserResponse> users = userService.findAllUsers();
        assertThat(users).hasSize(0);

        // given
        userService.create(userCreateRequest);
        userService.create(new UserCreateRequest("id", "pw", "name", "mail"));

        // when & then
        assertThat(userService.findAllUsers()).hasSize(2);
    }
}
