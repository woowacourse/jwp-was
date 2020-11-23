package utils;

import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HandlebarsTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void profileTest() throws Exception {
        Template template = TemplateUtils.buildTemplate("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        logger.debug("ProfilePage : {}", profilePage);
        assertAll(
                () -> assertThat(profilePage).contains(user.getName()),
                () -> assertThat(profilePage).contains(user.getEmail()),
                () -> assertThat(profilePage).contains(user.getUserId())
        );
    }

    @Test
    void listTest() throws Exception {
        Template template = TemplateUtils.buildTemplate("user/list");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        DataBase.addUser(user);
        Collection<User> users = DataBase.findAll();
        String listPage = template.apply(users);
        logger.debug("ProfilePage : {}", listPage);
        assertAll(
                () -> assertThat(listPage).contains(user.getName()),
                () -> assertThat(listPage).contains(user.getEmail()),
                () -> assertThat(listPage).contains(user.getUserId())
        );
    }
}
