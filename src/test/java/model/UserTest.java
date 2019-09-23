package model;

import org.junit.jupiter.api.Test;
import utils.QueryStringUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {


    @Test
    void 잘못된_쿼리정보로_유저_생성() {
        String query = "userId=javajigi&haha=password&name=박재성&email=javajigi@slipp.net";
        Map<String, String> info = QueryStringUtils.parse(query);

        assertThrows(IllegalArgumentException.class, () -> User.fromMap(info));
    }

    @Test
    void 쿼리정보로_유저_생성() throws IllegalAccessException {
        String query = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";
        Map<String, String> info = QueryStringUtils.parse(query);

        User testUser = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        User user = User.fromMap(info);
        assertThat(user).isEqualTo(testUser);
    }

}