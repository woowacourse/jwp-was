package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import dto.Users;
import model.User;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("indexModifier", (Helper<Integer>)(index, options) -> index + 1);

        Template template = handlebars.compile("user/list");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        User doo = new User("doogang", "1234", "doo", "test@test.com");
        Users users = new Users(Arrays.asList(user, doo));

        String listPage = template.apply(users);
        log.debug("ListPage : {}", listPage);

        assertAll(
            () -> assertThat(listPage.contains("javajigi")),
            () -> assertThat(listPage.contains("password")),
            () -> assertThat(listPage.contains("자바지기")),
            () -> assertThat(listPage.contains("javajigi@gmail.com")),
            () -> assertThat(listPage.contains("doogang")),
            () -> assertThat(listPage.contains("1234")),
            () -> assertThat(listPage.contains("doo")),
            () -> assertThat(listPage.contains("test@test.com"))
        );
    }
}
