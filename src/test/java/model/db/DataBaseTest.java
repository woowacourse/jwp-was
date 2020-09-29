package model.db;

import model.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DataBaseTest {
    @DisplayName("데이터베이스에서 UserId로 유저 검색")
    @Test
    void findByUserId() {
        DataBase.addUser(new User("1", "1234", "abc", "e"));

        User user = DataBase.findUserById("1").get();

        assertThat(user).isNotNull();
    }

    @DisplayName("데이터베이스에서 UserId로 유저 검색 - 일치하는 유저가 없는 경우")
    @Test
    void findByUserIdWhenEmpty() {
        Optional<User> user = DataBase.findUserById("1");

        assertThat(user.isPresent()).isFalse();
    }
}
