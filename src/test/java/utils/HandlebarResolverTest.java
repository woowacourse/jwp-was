package utils;

import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarResolverTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarResolverTest.class);
    public static final String ID_1 = "id1";
    public static final String ID_2 = "id2";

    @Test
    void 동적_html_body_생성() throws IOException {
        User user1 = new User(ID_1, "password", "name", "email");
        User user2 = new User(ID_2, "password", "name", "email");
        List<User> users = Arrays.asList(user1, user2);

        String path = "/user/list.html";
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String body = HandlebarResolver.getBody(path, model);

        assertThat(body.contains(ID_1)).isTrue();
        assertThat(body.contains(ID_2)).isTrue();

    }

}