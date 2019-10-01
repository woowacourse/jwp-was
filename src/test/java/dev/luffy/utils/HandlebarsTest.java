package dev.luffy.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.luffy.db.DataBase;
import dev.luffy.model.User;
import dev.luffy.model.UserCollection;

public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }

    @Test
    void allUsers() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("index", (value, option) -> {
            Integer intValue = (Integer) value;
            return intValue + 1;
        });

        Template template = handlebars.compile("user/list");

        User user1 = new User("javajigi1", "password", "자바지기1", "javajigi@gmail.com");
        User user2 = new User("javajigi2", "password", "자바지기2", "javajigi@gmail.com");

        DataBase.addUser(user1);
        DataBase.addUser(user2);

        UserCollection userCollection = new UserCollection(DataBase.findAll());

        String listPage = template.apply(userCollection);

        log.debug("ListPage : {}", listPage);
    }
}
