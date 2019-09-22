package model;

import org.junit.jupiter.api.Test;
import utils.QueryStringUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {


    @Test
    void 잘못된_쿼리정보로_유저_생성() {
        String queryString = "userId=javajigi&haha=password&name=박재성&email=javajigi@slipp.net";
        Map<String, String> query = QueryStringUtils.parse(queryString);

        User user = new User(query.get("userId"), query.get("password"),
                query.get("name"), query.get("email"));

        assertNull(user.getPassword());
    }

    @Test
    void 쿼리정보로_유저_생성() {
        String queryString = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";
        Map<String, String> query = QueryStringUtils.parse(queryString);

        User testUser = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        User user = new User(query.get("userId"), query.get("password"),
                query.get("name"), query.get("email"));

        assertThat(user).isEqualTo(testUser);
    }

}