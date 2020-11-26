package utils;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @DisplayName("동적 페이지 생성")
    @Test
    void apply() throws Exception {
        Map<String, String> users = new HashMap<>();
        users.put("userId", "javajigi");
        users.put("password", "password");
        users.put("name", "자바지기");
        users.put("email", "javajigi@gmail.com");

        String profilePage = HandlebarUtils.apply("profile", users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
