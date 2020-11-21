package db;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;

class UserDataBaseTest {
    @DisplayName("User를 저장한다.")
    @Test
    void addUser() {
        User user = new User("mozzi", "1234", "moon", "mozzi@wooteco");

        UserDataBase.addUser(user);

        assertThat(UserDataBase.findUserById(user.getUserId())).isEqualTo(user);
    }

    @DisplayName("userId에 해당하는 User를 조회한다.")
    @Test
    void findUserById() {
        User mozzi = new User("mozzi", "1234", "moon", "mozzi@wooteco");
        User dong = new User("dong", "4567", "guemdong", "dong@wooteco");
        UserDataBase.addUser(mozzi);
        UserDataBase.addUser(dong);

        User persist = UserDataBase.findUserById(mozzi.getUserId());

        assertThat(persist).isEqualTo(mozzi);
        assertThat(persist).isNotEqualTo(dong);
    }

    @DisplayName("모든 User를 조회한다.")
    @Test
    void findAll() {
        User mozzi = new User("mozzi", "1234", "moon", "mozzi@wooteco");
        User dong = new User("dong", "4567", "guemdong", "dong@wooteco");
        UserDataBase.addUser(mozzi);
        UserDataBase.addUser(dong);

        Collection<User> all = UserDataBase.findAll();

        assertThat(all).contains(mozzi, dong);
    }
}