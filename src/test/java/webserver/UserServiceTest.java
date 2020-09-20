package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class UserServiceTest {

    @DisplayName("User 생성 후 DB에 저장한다.")
    @Test
    void createUserTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("userId", "javajigi");
        params.put("password", "password");
        params.put("name", "박재성");
        params.put("email", "javajigi@slipp.net");

        // when
        User user = UserService.join(params);

        // then
        assertThat(DataBase.findAll().contains(user)).isTrue();
    }
}