package service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;

class UserServiceTest {

    @DisplayName("요청한 유저 정보를 저장한다.")
    @Test
    void userSignInTest() {
        UserService.signIn("userId=Id&password=password&name=ramen&email=mail@gmail.com");

        User id = DataBase.findUserById("Id");
        assertThat(id.getName()).isEqualTo("ramen");
    }

}