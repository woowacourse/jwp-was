package domain.user.service;

import db.DataBase;
import domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserServiceTest {

    @DisplayName("findById 일치하는 ID가 있을 때")
    @Test
    void findById() {
        User testUser = new User("1", "123", "js", "js@gmail.com");
        DataBase.addUser(testUser);

        User user = UserService.findById("1").orElseThrow(IllegalArgumentException::new);

        assertThat(user).isEqualToComparingFieldByField(testUser);
    }

    @DisplayName("findById 일치하는 ID가 없을 때")
    @Test
    void findById2() {
        User testUser = new User("1", "123", "js", "js@gmail.com");
        DataBase.addUser(testUser);

        Optional<User> user = UserService.findById("77");

        assertThat(user.isPresent()).isFalse();
    }
}