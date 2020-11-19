package jwp.was.webapplicationserver.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.util.Arrays;
import jwp.was.webapplicationserver.configure.controller.handlebar.RowNumberHelper;
import jwp.was.webapplicationserver.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlebarsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void handlebarTest() throws Exception {
        User user1 = new User("1", "1pass", "1name", "1email");
        User user2 = new User("2", "2pass", "2name", "2email");
        Object model = Arrays.asList(user1, user2);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/webapp");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("rowNumber", new RowNumberHelper());
        Template template = handlebars.compile("user/list");
        String html = template.apply(model);

        LOGGER.debug("ProfilePage : {}", html);
    }

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/webapp");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        LOGGER.debug("ProfilePage : {}", profilePage);
    }
}
