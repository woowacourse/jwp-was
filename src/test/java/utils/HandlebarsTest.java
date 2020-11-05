package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @DisplayName("템플릿을 적용한다. - 여러 User")
    @Test
    void applyTemplate() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user1 = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User user2 = new User("elly", "password", "엘리", "elly@gmail.com");
        User user3 = new User("bebop", "password", "비밥", "bebop@gmail.com");

        Users users = new Users(Arrays.asList(user1, user2, user3));
        String profilePage = template.apply(users);
        log.debug("ProfilePage : {}", profilePage);
    }
}
