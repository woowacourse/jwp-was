package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.model.User;

public class HandlebarsTest {

    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = User.builder()
            .userId("javajigi")
            .password("password")
            .name("자바지기")
            .email("javajigi@gmail.com")
            .build();

        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }
}
