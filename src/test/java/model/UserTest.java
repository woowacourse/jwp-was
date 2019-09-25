package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User("userId", "password", "name", "email");
    }

    @Test
    @DisplayName("유저 생성 테스트")
    void createUser() {
        assertDoesNotThrow(() -> new User("test", "test", "test", "test"));
    }

    @Test
    @DisplayName("각 필드 Get Test")
    void getTest() {
        assertThat(user.getUserId()).isEqualTo("userId");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getEmail()).isEqualTo("email");
    }

    @Test
    void matchPassword() {
        assertTrue(user.matchPassword("password"));
        assertFalse(user.matchPassword("wrongPassword"));
    }
}