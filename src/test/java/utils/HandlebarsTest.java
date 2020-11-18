package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.UserRepository;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

class HandlebarsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        UserRepository.addUser(user);
        Collection<User> users = UserRepository.findAll();
        String profilePage = template.apply(users);

        LOGGER.debug("ProfilePage : {}", profilePage);
    }
}
