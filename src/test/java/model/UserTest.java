package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    private static final String TEST_ID = "testId";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_NAME = "testName";
    private static final String TEST_EMAIL = "test@test.com";

    @Test
    @DisplayName("Id, Password, Name, Eamil을 입력받아 정상적으로 유저를 생성한다.")
    void createUser() {
        User user = User.of("testId", "testPassword", "testName", "test@test.com");
        User user2 = User.of("testId", "testPassword", "testName", "test@test.com");
        User user3 = User.of("testId2", "testPassword", "testName", "test@test.com");

        assertThat(user).isEqualTo(user2);
        assertThat(user).isNotEqualTo(user3);
        assertThat(user.toString()).contains(TEST_ID).contains(TEST_PASSWORD).contains(TEST_NAME).contains(TEST_EMAIL);
    }

    @Test
    @DisplayName("유저의 비밀번호가 일치한지 확인한다.")
    void isMatchPassword() {
        User user = User.of("testId", "testPassword", "testName", "test@test.com");
        assertTrue(user.isMatchPassword(TEST_PASSWORD));
    }

    @Test
    @DisplayName("유저의 비밀번호가 일치하지 않는 경우 false를 리턴한다.")
    void notMatchPassword() {
        User user = User.of("testId", "testPassword", "testName", "test@test.com");
        assertFalse(user.isMatchPassword("differentPassword"));
    }
}