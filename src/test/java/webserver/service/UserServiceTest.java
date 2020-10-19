package webserver.service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    private static final String TEST_USER_ID = "coollime";
    private static final String TEST_PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        User testUser = new User(TEST_USER_ID, TEST_PASSWORD, "쿨라임", "coollime@woowa.com");
        DataBase.addUser(testUser);
    }

    @DisplayName("회원이면 true를 반환")
    @Test
    void isUserTest() {
        HttpBody httpBody = DefaultHttpBody.from("userId=" + TEST_USER_ID + "&password=" + TEST_PASSWORD);

        assertThat(UserService.isUser(httpBody)).isTrue();
    }

    @DisplayName("비회원이면 false를 반환")
    @Test
    void isNonUserTest() {
        String nonUserId = "abcd";
        String nonUserPassword = "4321";
        HttpBody httpBody = DefaultHttpBody.from("userId=" + nonUserId + "&password=" + nonUserPassword);

        assertThat(UserService.isUser(httpBody)).isFalse();
    }

    @DisplayName("회원이나 DataBase에 저장된 비밀번호와 요청에 담긴 비밀번호가 다르면 false를 반환")
    @Test
    void isUserButInvalidPasswordTest() {
        String invalidPassword = "4321";
        HttpBody httpBody = DefaultHttpBody.from("userId=" + TEST_USER_ID + "&password=" + invalidPassword);

        assertThat(UserService.isUser(httpBody)).isFalse();
    }
}