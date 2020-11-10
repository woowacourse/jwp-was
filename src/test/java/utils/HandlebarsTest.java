package utils;

import java.util.Arrays;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @DisplayName("동적 페이지 생성")
    @Test
    void apply() throws Exception {
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User otherUser = new User("bingbong", "password", "빙봉", "bingbong@gmail.com");
        List<User> users = Arrays.asList(user, otherUser);

        String profilePage = HandlebarUtils.apply("user/profile", users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
