package was.template;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HandlebarsTest;
import was.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HandlebarTemplatesTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void 힌트_테스트를_이용한_핸들바_적용() {
        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");

        HandlebarTemplates handlebarTemplates = new HandlebarTemplates("/templates", "user/profile", ".html");
        String profilePage = handlebarTemplates.apply(user);

        log.debug("ProfilePage : {}", profilePage);

        assertTrue(profilePage.contains("자바지기"));
        assertTrue(profilePage.contains("javajigi@gmail.com"));
    }

    @Test
    void 유저_리스트_Handlebars_를_이용한_Parsing() {
        List<User> users = new ArrayList<>();

        User user1 = new User("ddu0422", "1234", "mir", "ddu0422@naver.com");
        User user2 = new User("abc", "1234", "abcd", "abc@naver.com");
        users.add(user1);
        users.add(user2);

        HandlebarTemplates handlebarTemplates = new HandlebarTemplates("/templates", "user/list", ".html");
        handlebarTemplates.put("users", users);
        String profilePage = handlebarTemplates.apply();

        log.debug("ProfilePage: {}", profilePage);

        assertTrue(profilePage.contains("ddu0422"));
        assertTrue(profilePage.contains("mir"));
        assertTrue(profilePage.contains("ddu0422@naver.com"));

        assertTrue(profilePage.contains("abc"));
        assertTrue(profilePage.contains("abcd"));
        assertTrue(profilePage.contains("abc@naver.com"));
    }
}
