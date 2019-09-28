package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest {
    private static final User DEFAULT_USER = User.of("id", "password", "name", "email");
    private static final User DIFFERENT_USER = User.of("id2", "password2", "name2", "email2");

    @Test
    @DisplayName("Database에 유저를 저장한다.")
    void addUser() {
        Database.addUser(DEFAULT_USER);

        assertThat(Database.findUserById("id").orElse(DIFFERENT_USER)).isEqualTo(DEFAULT_USER);
    }

    @Test
    @DisplayName("Database에 있는 모든 유저를 가져온다.")
    void findAll() {
        Database.addUser(DEFAULT_USER);
        Database.addUser(DIFFERENT_USER);

        assertTrue(Database.findAll().size() >= 2);
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저를 찾는다.")
    void findUserByIdAndPassword() {
        Database.addUser(DEFAULT_USER);
        Optional<User> maybeUser = Database.findUserByIdAndPassword(DEFAULT_USER.getId(), DEFAULT_USER.getPassword());
        assertThat(maybeUser.get()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @DisplayName("비밀번호가 다른 경우 유저를 찾지 못한다.")
    void findUserByIdAndPasswordFail() {
        Database.addUser(DEFAULT_USER);
        assertFalse(Database.findUserByIdAndPassword(DEFAULT_USER.getId(), DIFFERENT_USER.getPassword()).isPresent());
    }

    @Test
    @DisplayName("아이디가 다른 경우 유저를 찾지 못한다.")
    void findUserByIdAndPasswordFail2() {
        Database.addUser(DEFAULT_USER);
        assertFalse(Database.findUserByIdAndPassword(DIFFERENT_USER.getId(), DEFAULT_USER.getPassword()).isPresent());
    }
}