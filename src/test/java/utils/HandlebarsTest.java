package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsTest {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        final TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/static");
        loader.setSuffix(".html");
        final Handlebars handlebars = new Handlebars(loader);
        final Template template = handlebars.compile("user/profile");
        final User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        final String profilePage = template.apply(user);
        logger.debug("ProfilePage : {}", profilePage);
    }
}