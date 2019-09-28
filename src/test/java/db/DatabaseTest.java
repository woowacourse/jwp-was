package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
}