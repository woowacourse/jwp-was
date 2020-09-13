package service;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.USER_EMAIL;
import static util.Constants.USER_ID;
import static util.Constants.USER_NAME;
import static util.Constants.USER_PASSWORD;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.dto.UserRequest;

class UserServiceTest {

    private DataBase dataBase = new DataBase();
    private UserService userService = new UserService(dataBase);

    @DisplayName("User 생성 테스트")
    @Test
    void createUser() {
        UserRequest userRequest = new UserRequest(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
        User user = userService.createUser(userRequest);

        assertThat(user).isEqualTo(userRequest.toUser());
        assertThat(user.getUserId()).isEqualTo(userRequest.getUserId());
        assertThat(user.getPassword()).isEqualTo(userRequest.getPassword());
        assertThat(user.getName()).isEqualTo(userRequest.getName());
        assertThat(user.getEmail()).isEqualTo(userRequest.getEmail());

        assertThat(dataBase.findAll()).hasSize(1);
        assertThat(dataBase.findUserById(user.getUserId())).isEqualTo(user);
    }
}